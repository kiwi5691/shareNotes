package cn.sharenotes.wxapi.web.sysmsg;

import cn.sharenotes.core.service.SysMsgService;
;
import cn.sharenotes.core.utils.JacksonUtil;
import cn.sharenotes.core.utils.MsgUtils;
import cn.sharenotes.core.utils.ResponseUtil;
import cn.sharenotes.db.domain.SysMsg;
import cn.sharenotes.db.model.dto.MsgDetailDTO;
import cn.sharenotes.db.model.dto.SysMsgDto;
import cn.sharenotes.wxapi.annotation.LoginUser;
import com.alibaba.druid.support.json.JSONUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author hu
 */
@RestController
@RequestMapping("/wx/sysmsg")
public class SysMsgController {
    @Resource
    private SysMsgService sysMsgService;
    @ApiOperation(value = "通过获取详细信息")
    @GetMapping("/getDetail/{msg_id}")
    public Object getAllCategories(/*@LoginUser Integer userId,*/ @PathVariable("msg_id") Integer msg_id){
        Integer recentid =5;
        List<SysMsgDto> sysMsgs = sysMsgService.getSysMsg(recentid);
        if(CollectionUtils.isEmpty(sysMsgs)){
             return ResponseUtil.fail(1101,"您还没有消息");
        }
        Map<String, Object> result = new HashMap<>();
//        TODO 如果匿名         avatar=     http://kiwi1.cn/upload/2019/10/my-0c1c93fd19a7415eb26b4fd5ed0c25cc.png  ,图片上传做完再换 地址
        result.put("avatar", sysMsgs);
        result.put("userName", sysMsgs);
        result.put("title", sysMsgs);
        result.put("cateName", sysMsgs);
        result.put("post_id", sysMsgs);
        result.put("type", sysMsgs);
        result.put("content", sysMsgs);
        result.put("cateid", sysMsgs);
        return ResponseUtil.ok(result);
    }
    @ApiOperation(value = "通过获取消息list")
    @GetMapping("/getAllMsg")
    public Object getAllMsg(/*@LoginUser Integer userId,*/ ){
        MsgUtils.getType(1);
        Integer recentid =5;
        // TODO: 2019/10/12  type 1 评论 ，2 好友，3系统
        // TODO: 2019/10/12  返回type( MsgUtils.getType(1)的String)，content，msgId  合成的msgList  dto
        List<SysMsgDto> sysMsgs = sysMsgService.getSysMsg(recentid);
        if(CollectionUtils.isEmpty(sysMsgs)){
            return ResponseUtil.fail(1101,"您还没有消息");
        }
        Map<String, Object> result = new HashMap<>();

// TODO       private MsgDetailDTO msgDetailDTO;

        result.put("type", sysMsgs);

        return ResponseUtil.ok(result);
    }
    @ApiOperation(value = "通过 msgid 删除评论")
    @GetMapping("/delete")
    public Object delectMsg(/*@LoginUser Integer userId,*/ @RequestBody String body){
        Integer msg_id = JacksonUtil.parseInteger(body, "msg_id");
        if(sysMsgService.delectMsgById(msg_id)>0){
            return ResponseUtil.ok();
        }

        return ResponseUtil.fail();
    }

    @ApiOperation(value = "删除所有评论")
    @GetMapping("/deleteAll")
    public Object delectMsgAll(@LoginUser Integer userId){
        if(true){
            return ResponseUtil.ok();
        }

        return ResponseUtil.fail();
    }

    @ApiOperation(value = "获取消息数量")
    @GetMapping("/getNum")
    public Object getAllNum(/*@LoginUser Integer userId,*/ ){
        Integer recentid =5;
        Integer sysMsgsnum = sysMsgService.getSysMsgNum(recentid);
        Map<String, Object> result = new HashMap<>();

        result.put("sysMsgsnum", sysMsgsnum);
        return ResponseUtil.ok(result);
    }
}
