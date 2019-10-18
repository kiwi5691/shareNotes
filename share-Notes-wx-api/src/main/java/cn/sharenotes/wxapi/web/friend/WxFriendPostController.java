package cn.sharenotes.wxapi.web.friend;

import cn.sharenotes.core.redis.KeyPrefix.VisitLimitKey;
import cn.sharenotes.core.redis.RedisManager;
import cn.sharenotes.core.service.PostCommentService;
import cn.sharenotes.core.service.PostContentService;
import cn.sharenotes.core.utils.ContentUtils;
import cn.sharenotes.core.utils.ResponseUtil;
import cn.sharenotes.db.model.dto.PostCommentDto;
import cn.sharenotes.db.model.dto.PostDTO;
import cn.sharenotes.wxapi.annotation.LoginUser;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/wx/friend")
public class WxFriendPostController {

    @Resource
    private RedisManager redisManager;
    @Resource
    private PostContentService postContentService;
    @Resource
    private PostCommentService postCommentService;

    @ApiOperation("Lists posts")
    @GetMapping("/getPost/{cate_id}")
    public Object getPosts(@LoginUser Integer userId,@PathVariable("cate_id") Integer cate_id) {

        List<PostDTO> postDTOS = postContentService.findPostsByCateId( cate_id);
        Map<String, Object> result = new HashMap<>();
        if (CollectionUtils.isEmpty(postDTOS)) {
            return ResponseUtil.fail(711,"您朋友尚未创建文章");
        } else {
            result.put("posts", postDTOS);
            return ResponseUtil.ok(result);
        }
    }


    @ApiOperation("文章详细")
    @GetMapping("/getDetail/{post_id}")
    public Object getPostDetail(@LoginUser Integer userId, @PathVariable("post_id") Integer post_id) {

        List<PostDTO> postDTOS = postContentService.findPostsByCateId(post_id);
        if (CollectionUtils.isEmpty(postDTOS)) {
            return ResponseUtil.fail(809,"未知错误");
        } else {
            //添加访问限制
            Integer userLimit=0;
            userLimit=redisManager.get(VisitLimitKey.board, "userId :"+userId+"post_id :"+post_id, Integer .class);
            if(userLimit==null||userLimit.equals(0)){
                redisManager.set(VisitLimitKey.board, "userId :"+userId+"post_id :"+post_id,1);
                postCommentService.IncrVisit(post_id);
            }

            Map<String, Object> result = new HashMap<>();
            PostCommentDto postCommentDto= postCommentService.findPostsByPostId(post_id);
            result.put("originalContent", postCommentDto.getOriginalContent());
            result.put("type", ContentUtils.getType( postCommentDto.getType()));
            result.put("visits", postCommentDto.getVisits());
            result.put("title", postCommentDto.getTitle());
            result.put("switch1", ContentUtils.getTypeInBoolean(postCommentDto.getType()));
            result.put("updateTime", postCommentDto.getUpdateTime());
            result.put("createTime", postCommentDto.getCreateTime());
            result.put("baseComment", postCommentDto.getCommentDtoList());
            result.put("allowComment", ContentUtils.returnTypeInBoolean(postCommentDto.getDisallowComment()));

            return ResponseUtil.ok(result);
        }
    }




}
