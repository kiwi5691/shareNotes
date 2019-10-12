package cn.sharenotes.wxapi.web.sysmsg;

import cn.sharenotes.core.service.SysMsgService;
;
import cn.sharenotes.core.utils.ResponseUtil;
import cn.sharenotes.db.domain.SysMsg;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;


/**
 * @author hu
 */
@RestController
@RequestMapping("/wx/sysmsg")
public class SysMsgController {
    @Resource
    private SysMsgService sysMsgService;
    @ApiOperation(value = "通过 recentid 获取评论")
    @GetMapping("/getAll/{recentid}")
    public Object getAllCategories(/*@LoginUser Integer userId,*/ @PathVariable("recentid") Integer recentid){
        List<SysMsg> sysMsgs = sysMsgService.getSysMsg(recentid);
        if(CollectionUtils.isEmpty(sysMsgs)){
             return ResponseUtil.fail(1101,"您还没有消息");
        }

        return ResponseUtil.ok(sysMsgs);
    }
    @ApiOperation(value = "通过 recentid 获取评论数量")
    @GetMapping("/getNum/{recentid}")
    public Object getAllNum(/*@LoginUser Integer userId,*/ @PathVariable("recentid") Integer recentid){
        Integer sysMsgsnum = sysMsgService.getSysMsgNum(recentid);

        return ResponseUtil.ok(sysMsgsnum);
    }
}
