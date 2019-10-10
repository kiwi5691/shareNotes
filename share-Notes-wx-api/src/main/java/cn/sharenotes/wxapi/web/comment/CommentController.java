package cn.sharenotes.wxapi.web.comment;

import cn.sharenotes.core.service.CommentService;

import cn.sharenotes.core.utils.JacksonUtil;
import cn.sharenotes.core.utils.ResponseUtil;
import cn.sharenotes.wxapi.annotation.LoginUser;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author hu
 */
@Slf4j
@RestController
@RequestMapping("/wx/comment")
public class CommentController {
    @Resource
    private CommentService commentService;
    @ApiOperation(value = "获得int userid, int postId,String content, Boolean isanonymous")
    @PostMapping("/add")
        public Object addComment(/*@LoginUser Integer userId,*/ @RequestBody String body){
        Integer userId = 5;
        Integer postId = JacksonUtil.parseInteger(body, "postId");
        String content = JacksonUtil.parseString(body, "content");
        boolean isanonymous = JacksonUtil.parseBoolean(body, "isanonymous");

        if(content.isEmpty()){
            return ResponseUtil.fail(901, "添加评论失败，文章没内容");
        }
        if(commentService.addComment(userId,postId,content,isanonymous)>0){
            return ResponseUtil.ok();
        }
        return ResponseUtil.fail(999, "未知错误");
        }
    @ApiOperation(value = "通过 评论Id 删除目录")
    @DeleteMapping("/delete/")
    public Object deleteCategory(/*@LoginUser Integer userId,*/@RequestBody String body) {
        Integer userId = 5;
        Integer commentId = JacksonUtil.parseInteger(body, "commentId");
        if (commentService.delectComment(commentId) > 0) {
            return ResponseUtil.ok();
        }
         return ResponseUtil.fail(999, "未知错误");
    }



}
