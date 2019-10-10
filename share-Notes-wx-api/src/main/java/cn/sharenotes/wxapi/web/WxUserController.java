package cn.sharenotes.wxapi.web;

import cn.sharenotes.core.enums.ContentBase;
import cn.sharenotes.core.jms.ActiveMQService;
import cn.sharenotes.core.redis.KeyPrefix.IssueSubmitKey;
import cn.sharenotes.core.redis.RedisManager;
import cn.sharenotes.core.utils.EmailTemplate;
import cn.sharenotes.core.utils.JacksonUtil;
import cn.sharenotes.core.utils.ResponseUtil;
import cn.sharenotes.wxapi.annotation.LoginUser;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户服务
 * @author kiwi
 */
@Slf4j
@RestController
@RequestMapping("/wx/user")
@Validated
public class WxUserController {

    final private  String SendTo= "805344479@qq.com";
    @Resource
    private RedisManager redisManager;
    @Resource
    private ActiveMQService activeMQService;

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



    @ApiOperation(value = "用户提交issue")
    @PostMapping("submitIssue")
    public Object submitIssue(@LoginUser Integer userId,@RequestBody String body) throws IOException {

        Integer submitTimes;
        String titleName = JacksonUtil.parseString(body, "titleName");
        String context = JacksonUtil.parseString(body, "context");
        if (userId == null) {
            return ResponseUtil.fail(102,"请登录");
        }
        else if (context.length()< ContentBase.LIMITWORDS.getValue()) {
            return ResponseUtil.fail(102,"最少五个字");
        }

        submitTimes=redisManager.get(IssueSubmitKey.board, "userId :"+userId, Integer .class);
        if (submitTimes == null){
            submitTimes=1;
            redisManager.set(IssueSubmitKey.board, "userId :"+userId, submitTimes);
        }
        else if(submitTimes <= ContentBase.LIMITTIMES.getValue()){
            redisManager.incr(IssueSubmitKey.board, "userId :"+userId);
        }else if(submitTimes > ContentBase.LIMITTIMES.getValue()){
            return ResponseUtil.fail(102,"今日提交issue上限");
        }


        activeMQService.sendEmail(SendTo,"issue By:"+titleName, EmailTemplate.issueTemplate(titleName,context));

        return ResponseUtil.ok();
    }

}