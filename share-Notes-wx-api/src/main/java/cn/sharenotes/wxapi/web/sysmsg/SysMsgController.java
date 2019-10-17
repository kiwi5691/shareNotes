package cn.sharenotes.wxapi.web.sysmsg;

import cn.sharenotes.core.service.SysMsgService;
;
import cn.sharenotes.core.utils.JacksonUtil;
import cn.sharenotes.core.utils.MsgUtils;
import cn.sharenotes.core.utils.ResponseUtil;
import cn.sharenotes.db.model.dto.SysMsgDto;
import cn.sharenotes.wxapi.annotation.LoginUser;
import cn.sharenotes.db.model.dto.MsgListDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
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
    public Object getAllCategories(@LoginUser Integer userId, @PathVariable("msg_id") Integer msg_id){
        SysMsgDto sysMsgDto  = new SysMsgDto();
        sysMsgDto = sysMsgService.getMsgPostById(msg_id);
        if(sysMsgDto == null){
             return ResponseUtil.fail(1101,"您还没有消息");
        }
        Map<String, Object> result = new HashMap<>();
        result.put("avatar", sysMsgDto.getSysMsg().getEmail());
        result.put("userName", sysMsgDto.getComments().getAuthor());
        result.put("title", sysMsgDto.getPosts().getTitle());
        result.put("cateName", sysMsgDto.getCategories().getName());
        result.put("post_id", sysMsgDto.getPosts().getId());
        result.put("type", sysMsgDto.getSysMsg().getType());
        result.put("content", sysMsgDto.getComments().getContent());
        result.put("cateid", sysMsgDto.getCategories().getId());
        return ResponseUtil.ok(result);
    }
    @ApiOperation(value = "通过获取消息list")
    @GetMapping("/getAllMsg")
    public Object getAllMsg(@LoginUser Integer userId){
        MsgUtils.getType(1);
        // TODO: 2019/10/12  type 1 评论 ，2 好友，3系统
        // TODO: 2019/10/12  返回type( MsgUtils.getType(1)的String)，content，msgId  合成的msgList  dto
        List<MsgListDto> sysMsgs = sysMsgService.getSysMsg(userId);
        if(CollectionUtils.isEmpty(sysMsgs)){
            return ResponseUtil.fail(1101,"您还没有消息");
        }
        Map<String, Object> result = new HashMap<>();
            result.put("msgList", sysMsgs);
        return ResponseUtil.ok(result);
    }
    @ApiOperation(value = "通过 msgid 删除评论")
    @DeleteMapping("/delete")
    public Object delectMsg(@LoginUser Integer userId,@RequestBody String body){
        Integer msg_id = JacksonUtil.parseInteger(body, "msg_id");
        if(sysMsgService.delectMsgById(msg_id)>0){
            return ResponseUtil.ok();
        }

        return ResponseUtil.fail(2223,"删除失败");
    }

    @ApiOperation(value = "删除所有评论")
    @DeleteMapping("/deleteAll")
    public Object delectMsgAll(@LoginUser Integer userId){
        if(sysMsgService.delectMsgByRecentId(userId)>0){
            return ResponseUtil.ok();
        }

        return ResponseUtil.fail(2223,"删除失败");
    }

    @ApiOperation(value = "获取消息数量")
    @GetMapping("/getNum")
    public Object getAllNum(@LoginUser Integer userId ){
        Integer sysMsgsnum = sysMsgService.getSysMsgNum(userId);
        Map<String, Object> result = new HashMap<>();

        result.put("sysMsgsnum", sysMsgsnum);
        return ResponseUtil.ok(result);
    }
}
