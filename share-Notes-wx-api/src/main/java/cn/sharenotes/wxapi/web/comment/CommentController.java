package cn.sharenotes.wxapi.web.comment;

import cn.binarywang.wx.miniapp.api.WxMaSecCheckService;
import cn.sharenotes.core.service.CommentService;
import cn.sharenotes.core.service.PostCommentService;
import cn.sharenotes.core.utils.JacksonUtil;
import cn.sharenotes.core.utils.ResponseUtil;
import cn.sharenotes.db.domain.Comments;
import cn.sharenotes.db.model.dto.CommentDto;
import cn.sharenotes.db.model.dto.PostCommentDto;
import cn.sharenotes.wxapi.annotation.LoginUser;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hu
 */
@Slf4j
@RestController
@RequestMapping("/wx/comment")
public class CommentController {
    @Resource
    private CommentService commentService;
    @Autowired
    private WxMaSecCheckService wxMaSecCheckService;
    @Resource
    private PostCommentService postCommentService;

    @ApiOperation(value = "获得int userid, int postId,String content, Boolean isanonymous")
    @PostMapping("/add")
        public Object addComment(@LoginUser Integer userId, @RequestBody String body){
        Integer postId = JacksonUtil.parseInteger(body, "postId");
        String content = JacksonUtil.parseString(body, "content");
        boolean isanonymous = JacksonUtil.parseBoolean(body, "isanonymous");

        if(content.isEmpty()){
            return ResponseUtil.fail(901, "添加评论失败，文章没内容");
        }
        boolean unSafe = true;
        try{
            unSafe =wxMaSecCheckService.checkMessage(content);
        }catch (WxErrorException e) {
            return ResponseUtil.fail(202, "添加评论失败，出现违禁词");
        }
        if(!unSafe){
            return ResponseUtil.fail(202, "添加评论失败，出现违禁词");
        }
        if(commentService.addComment(userId,postId,content,isanonymous)>0){
            return ResponseUtil.ok();
        }
        return ResponseUtil.fail(999, "未知错误");
        }

    @ApiOperation(value = "通过 评论Id 删除目录")
    @DeleteMapping("/delete/")
    public Object deleteCategory(@LoginUser Integer userId,@RequestBody String body) {
        Integer commentId = JacksonUtil.parseInteger(body, "commentId");
        if (commentService.delectComment(commentId) > 0) {
            return ResponseUtil.ok();
        }
        return ResponseUtil.fail(999, "未知错误");
    }
    @ApiOperation(value = "通过 postId 获得评论")
    @GetMapping("/get/{post_id}")
    public Object getPost(@LoginUser Integer userId, @PathVariable("post_id") Integer post_id){

        Map<String, Object> result = new HashMap<>();
        List<CommentDto> commentsList = postCommentService.findCommentByPostId(post_id);

        if(CollectionUtils.isEmpty(commentsList)){
            return ResponseUtil.fail(604,"用户文章下没有评论");
        }
        result.put("baseComment", commentsList);
        return ResponseUtil.ok(result);

    }

}
