package cn.sharenotes.wxapi.web.content;

import cn.sharenotes.core.service.PostCommentService;
import cn.sharenotes.core.service.PostContentService;
import cn.sharenotes.core.utils.JacksonUtil;
import cn.sharenotes.core.utils.ResponseUtil;
import cn.sharenotes.db.model.dto.PostCommentDto;
import cn.sharenotes.db.model.dto.PostDTO;
import cn.sharenotes.db.model.vo.PostContentVo;
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
    private PostCommentService postCommentService;

    @ApiOperation("获取所有文章")
    @GetMapping("/getAll/{cate_id}")
    public Object getPosts(/*@LoginUser Integer userId,*/ @PathVariable("cate_id") Integer cate_id) {
        List<PostDTO> postDTOS = postContentService.findPostsByCateId(cate_id);
        Map<String, Object> result = new HashMap<>();
        if (CollectionUtils.isEmpty(postDTOS)) {
            return ResponseUtil.fail(801, "您尚未创建文章");
        } else {
            result.put("posts", postDTOS);
            return ResponseUtil.ok(result);
        }
    }

    @ApiOperation(value = "添加文章")
    @PostMapping("/add")
    public Object addPostContent(/*@LoginUser Integer userId,*/ @RequestBody String body) {
        Integer categoryId = JacksonUtil.parseInteger(body, "categoryId");

        Integer userId = 5;

        PostContentVo postContentVo = getBodyIntoPostContentVo(userId, body);

        if (postContentVo == null) {
            return ResponseUtil.fail(802, "添加文章失败，文章标题已存在");
        }

        Integer integer = postContentService.addPostCategory(categoryId, postContentVo);

        if (integer > 0) {
            return ResponseUtil.ok();
        }
        return ResponseUtil.fail();
    }

    @ApiOperation(value = "删除文章")
    @DeleteMapping("delete/{postId}")
    public void deletePostContent(@PathVariable("postId") Integer postId) {
        Integer i = postContentService.deletePostContentAndCategory(postId);
        if(i > 0){
            ResponseUtil.ok();
        }
        ResponseUtil.fail();
    }

    @ApiOperation("文章详细")
    @GetMapping("/getDetail/{post_id}")
    public Object getPostDetail(/*@LoginUser Integer userId,*/ @PathVariable("post_id") Integer post_id) {
        Map<String, Object> result = new HashMap<>();
        PostCommentDto postCommentDto = postCommentService.findPostsByPostId(post_id);
        result.put("originalContent", postCommentDto.getOriginalContent());
        result.put("visits", postCommentDto.getVisits());
        result.put("title", postCommentDto.getTitle());
        result.put("switch1", postCommentDto.isNotallowComment());
        result.put("updateTime", postCommentDto.getUpdateTime());
        result.put("createTime", postCommentDto.getCreateTime());
        result.put("baseComment", postCommentDto.getCommentDtoList());
        return ResponseUtil.ok(result);
    }

    @ApiOperation(value = "将 body 转成 PostContentVo ")
    public PostContentVo getBodyIntoPostContentVo(Integer userId, String body) {
        String title = JacksonUtil.parseString(body, "title");
        boolean type = JacksonUtil.parseBoolean(body, "type");
        String originalContent = JacksonUtil.parseString(body, "originalContent");
        Integer categoryId = JacksonUtil.parseInteger(body, "categoryId");

        List<String> titles = postContentService.findAllPostsNameByCategoryId(categoryId);
        if (!CollectionUtils.isEmpty(titles)) {
            if (titles.contains(title)) {
                return null;
            }
        }

        PostContentVo postContentVo = new PostContentVo();
        postContentVo.setCreateFrom(userId);
        postContentVo.setTitle(title);
        postContentVo.setOriginalContent(originalContent);
        postContentVo.setFormatContent(originalContent.substring(0, 10));
        if (type) {
            postContentVo.setType(1);
        } else {
            postContentVo.setType(2);
        }
        return postContentVo;
    }

}
