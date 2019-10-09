package cn.sharenotes.wxapi.web.content;

import cn.sharenotes.core.service.PostCommentSerive;
import cn.sharenotes.core.utils.ResponseUtil;
import cn.sharenotes.db.model.dto.PostCommentDto;
import cn.sharenotes.db.model.dto.PostDTO;
import cn.sharenotes.core.service.PostContentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Post controller.
 *
 * @author kiwi
 * @date 3/19/19
 */
@RestController
@RequestMapping("/wx/posts")
public class WxPostController {


    @Resource
    private PostContentService postContentService;

    @Resource
    private PostCommentSerive postCommentSerive;

    @ApiOperation("获取所有文章")
    @GetMapping("/getAll/{cate_id}")
    public Object getPosts(/*@LoginUser Integer userId,*/ @PathVariable("cate_id") Integer cate_id) {
        List<PostDTO> postDTOS = postContentService.findPostsByUserId(3, cate_id);
        Map<String, Object> result = new HashMap<>();
        if (CollectionUtils.isEmpty(postDTOS)) {
            return ResponseUtil.fail(801,"您尚未创建文章");
        } else {
            result.put("posts", postDTOS);
            return ResponseUtil.ok(result);
        }
    }


    @ApiOperation("文章详细")
    @GetMapping("/getDetail/{post_id}")
    public Object getPostDetail(/*@LoginUser Integer userId,*/ @PathVariable("post_id") Integer post_id) {
//        TODO 1111111111111
        List<PostDTO> postDTOS = postContentService.findPostsByUserId(3, post_id);
        if (CollectionUtils.isEmpty(postDTOS)) {
            return ResponseUtil.fail(801,"您尚未创建文章");
        } else {

            //        TODO 1111111111111
            Map<String, Object> result = new HashMap<>();
            PostCommentDto postCommentDto= postCommentSerive.findPostsByPostId(post_id);
            result.put("originalContent", postCommentDto.getOriginalContent());
            result.put("visits", postCommentDto.getVisits());
            result.put("title", postCommentDto.getTitle());
            result.put("switch1", postCommentDto.isNotallowComment());
            result.put("updateTime", postCommentDto.getUpdateTime());
            result.put("createTime", postCommentDto.getCreateTime());
            result.put("baseComment", postCommentDto.getCommentDtoList());
            return ResponseUtil.ok(result);
        }
    }


// todo
//    @GetMapping("{postId:\\d+}")
//    public PostVO getBy(@PathVariable("postId") Integer postId) {
//            获取文章
//        return 文章;
//    }





    //      todo
//    @PostMapping
//    public PostVO createBy(@Valid @RequestBody  PostDto post,
//                                 @RequestParam(value = "autoSave", required = false, defaultValue = "false") Boolean autoSave) {
//        // TODO: 2019/9/28 创建文章，需要自动存储
//    }





// todo
//    @PutMapping("{postId:\\d+}")
//    public PostVO updateBy(@Valid @RequestBody PostDto postdto,
//                                 @PathVariable("postId") Integer postId,
//                                 @RequestParam(value = "autoSave", required = false, defaultValue = "false") Boolean autoSave) {
//         获取文章id
//        Post postToUpdate = postService.getById(postId);
//        postdto.update(postToUpdate);
//        自动更新
//        return 更新进数据库;
//    }



    @DeleteMapping("{postId:\\d+}")
    public void deletePermanently(@PathVariable("postId") Integer postId) {
//       todo 删除文章
    }


}
