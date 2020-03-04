package cn.sharenotes.wxapi.web;

import cn.binarywang.wx.miniapp.api.WxMaSecCheckService;
import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import cn.sharenotes.core.enums.ContentBase;
import cn.sharenotes.core.jms.ActiveMQService;
import cn.sharenotes.core.jms.EmailService;
import cn.sharenotes.core.notify.NotifyService;
import cn.sharenotes.core.notify.NotifyType;
import cn.sharenotes.core.redis.KeyPrefix.IssueSubmitKey;
import cn.sharenotes.core.redis.RedisManager;
import cn.sharenotes.core.service.UserFormIdService;
import cn.sharenotes.core.service.UserService;
import cn.sharenotes.core.utils.DateTimeUtil;
import cn.sharenotes.core.utils.EmailTemplate;
import cn.sharenotes.core.utils.JacksonUtil;
import cn.sharenotes.core.utils.ResponseUtil;
import cn.sharenotes.db.domain.User;
import cn.sharenotes.db.domain.UserFormid;
import cn.sharenotes.wxapi.annotation.LoginUser;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.async.DeferredResult;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;

/**
 * 用户服务
 * @author kiwi
 */
@Slf4j
@RestController
@RequestMapping("/wx/user")
@Validated
public class WxUserController {
    private  final String SendTo= "805344479@qq.com";
    private final String SENDSTATUS ="已成功";
    @Resource
    private UserService userService;
    @Resource
    private UserFormIdService userFormIdService;
    @Resource
    private NotifyService notifyService;
    @Resource
    private RedisManager redisManager;
    @Resource
    private EmailService emailService;

    @Autowired
    private WxMaSecCheckService wxMaSecCheckService;
    //为邮件发送创建线程池,这里使用无界任务队列 LinkedBlockingQueue
    public static ExecutorService emailExePool =new
            ThreadPoolExecutor(1, 10, 1000, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(),Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());

    @GetMapping("img/{msg}")
    public String img(@PathVariable("msg") File msg) throws WxErrorException {
        if(wxMaSecCheckService.checkImage(msg)){
            return  "ok";
        }else {
            return  "fail";

        }

    }
    /**
     * 用户个人页面数据
     *
     * @param userId 用户ID
     * @return 用户个人页面数据
     */
    @ApiOperation(value = "用户个人页面数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name ="userId",value = "用户id",paramType = "path",required = true,dataType = "Integer")
    })
    @GetMapping("index")
    public Object list(@LoginUser Integer userId) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        Map<Object, Object> data = new HashMap<Object, Object>();
        //显示个人信息
        data.put("order", "个人信息");
        return ResponseUtil.ok(data);
    }



    //这里使用DeferredResult异步调用请求
    @ApiOperation(value = "用户提交issue")
    @PostMapping("submitIssue")
    public DeferredResult<Object> submitIssue(@LoginUser Integer userId, @RequestBody String body) throws IOException {
        User user = userService.findById(userId);

        DeferredResult<Object> result = new DeferredResult<Object>(10*1000L);

        Integer submitTimes;
        String titleName = JacksonUtil.parseString(body, "titleName");
        String context = JacksonUtil.parseString(body, "context");
        if (userId == null) {
            result.setResult(ResponseUtil.fail(102,"请登录"));
            return result;
        }
        else if (context.length()< ContentBase.LIMITWORDS.getValue()) {
            result.setResult(ResponseUtil.fail(102,"最少五个字"));
            return result;
        }

        submitTimes=redisManager.get(IssueSubmitKey.board, "userId :"+userId, Integer .class);
        if (submitTimes == null){
            submitTimes=1;
            redisManager.set(IssueSubmitKey.board, "userId :"+userId, submitTimes);
        }
        else if(submitTimes <= ContentBase.LIMITTIMES.getValue()){
            redisManager.incr(IssueSubmitKey.board, "userId :"+userId);
        }else if(submitTimes > ContentBase.LIMITTIMES.getValue()){
            result.setResult(ResponseUtil.fail(102,"今日提交issue上限"));
            return   result;
        }
        result.onTimeout(new Runnable() {
            @Override
            public void run() {
                result.setResult(ResponseUtil.fail(102,"发送超时，请重新发送"));
            }
        });
        result.onCompletion(new Runnable() {
            @Override
            public void run() {

                result.setResult(ResponseUtil.ok());
            }
        });
        emailExePool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    emailService.sendEmail(SendTo,"issue By:"+titleName, EmailTemplate.issueTemplate(titleName,context));
//                    weChatNotify(titleName,user.getWeixinOpenid());
                    result.setResult(ResponseUtil.ok());
                } catch (Exception e) {
                    result.setResult(ResponseUtil.fail(102,"发送异常，请联系开发者"));
                }
            }
        });

        return result;
    }



    public void weChatNotify(String issueName,String openId){
        List<WxMaSubscribeMessage.Data> wxMaSubscribeDatas = new ArrayList<>();
        WxMaSubscribeMessage.Data wxMaSubscribeData = new WxMaSubscribeMessage.Data();
        wxMaSubscribeData.setName("phrase1");
        wxMaSubscribeData.setValue(SENDSTATUS);
        //每个参数 存放到大集合中
        wxMaSubscribeDatas.add(wxMaSubscribeData);
        wxMaSubscribeData=null;
        wxMaSubscribeData.setName("character_string2");
        wxMaSubscribeData.setValue(SendTo);
        //每个参数 存放到大集合中
        wxMaSubscribeDatas.add(wxMaSubscribeData);
        wxMaSubscribeData=null;
        wxMaSubscribeData.setName("thing3");
        wxMaSubscribeData.setValue(issueName);
        //每个参数 存放到大集合中
        wxMaSubscribeDatas.add(wxMaSubscribeData);
        wxMaSubscribeData=null;
        wxMaSubscribeData.setName("time4");
        wxMaSubscribeData.setValue(DateTimeUtil.getDateTimeDisplayString(LocalDateTime.now()));
        //每个参数 存放到大集合中
        wxMaSubscribeDatas.add(wxMaSubscribeData);

        notifyService.notifyWxTemplate(openId, NotifyType.ISSUE, wxMaSubscribeDatas);

    }

    /*
    * 废弃，微信再不支持模板消息
     */
    @Deprecated
    public void saveFormId(String formId,String openId){
        UserFormid userFormid = new UserFormid();
        userFormid.setOpenid(openId);
        userFormid.setFormid(formId);
        userFormid.setIsprepay(true);
        userFormid.setUseamount(1);
        userFormid.setExpireTime(new Date());
        userFormIdService.addUserFormId(userFormid);

    }
}