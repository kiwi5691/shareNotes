package cn.sharenotes.wxapi.web.content;

import cn.binarywang.wx.miniapp.api.WxMaSecCheckService;
import cn.sharenotes.core.aop.annotation.IndexMethod;
import cn.sharenotes.core.aop.annotation.MethodType;
import cn.sharenotes.core.aop.cache.IndexThreadLocal;
import cn.sharenotes.core.service.PostCommentService;
import cn.sharenotes.core.service.PostContentService;
import cn.sharenotes.core.utils.ContentUtils;
import cn.sharenotes.core.utils.JacksonUtil;
import cn.sharenotes.core.utils.ResponseUtil;
import cn.sharenotes.db.domain.PostsExample;
import cn.sharenotes.db.domain.PostsWithBLOBs;
import cn.sharenotes.db.domain.index.PostsIndex;
import cn.sharenotes.db.mapper.PostsMapper;
import cn.sharenotes.db.model.dto.PostCommentDto;
import cn.sharenotes.db.model.dto.PostDTO;
import cn.sharenotes.db.model.dto.PostTypeDTO;
import cn.sharenotes.db.model.vo.PostContentVo;
import cn.sharenotes.db.repository.PostsIndexRepository;
import cn.sharenotes.db.utils.DtoUtils;
import cn.sharenotes.wxapi.annotation.Log;
import cn.sharenotes.wxapi.annotation.LoginUser;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * Post controller.
 *
 * @author kiwi
 * @date 3/19/19
 */
@Slf4j
@RestController
@RequestMapping("/wx/posts")
public class WxPostController {

    @Resource
    private PostsIndexRepository postsIndexRepository;
    @Resource
    private PostsMapper postsMapper;

    @Resource
    private PostContentService postContentService;
    @Autowired
    private WxMaSecCheckService wxMaSecCheckService;
    @Resource
    private PostCommentService postCommentService;

    @ApiOperation("获取所有文章")
    @GetMapping("/getAll/{cate_id}")
    public Object getPosts(@LoginUser Integer userId, @PathVariable("cate_id") Integer cate_id) {
        List<PostTypeDTO> postTypeDTOS = new ArrayList<>();
        Map<String, Object> result = new HashMap<>();

        List<PostDTO> postDTOS = postContentService.findPostsByCateId(cate_id);

        if (CollectionUtils.isEmpty(postDTOS)) {
            return ResponseUtil.fail(801, "您尚未创建文章");
        }
        for (PostDTO postTypeDTO : postDTOS) {

            PostTypeDTO postTypeDTO1 = new PostTypeDTO(postTypeDTO.getId(), ContentUtils.getType(postTypeDTO.getType()), postTypeDTO.getUpdateTime(), postTypeDTO.getTitle(), postTypeDTO.getFormatContent());
            postTypeDTOS.add(postTypeDTO1);
        }
        result.put("posts", postTypeDTOS);
        return ResponseUtil.ok(result);

    }

    @Log("添加文章")
    @ApiOperation(value = "添加文章")
    @PostMapping("/add")
    public Object addPostContent(@LoginUser Integer userId, @RequestBody String body) {
        Integer categoryId = JacksonUtil.parseInteger(body, "categoryId");
        String originalContent = JacksonUtil.parseString(body, "originalContent");

        PostContentVo postContentVo = getBodyIntoPostContentVo(userId, body, "add");
        boolean unSafe = true;
        if(originalContent != null) {
            try{
                unSafe =wxMaSecCheckService.checkMessage(originalContent);
            }catch (WxErrorException e) {
                return ResponseUtil.fail(202, "添加文章失败，出现违禁词");
            }
        }
        if(!unSafe){
            return ResponseUtil.fail(202, "添加文章失败，出现违禁词");
        }
        if (postContentVo == null) {
            return ResponseUtil.fail(802, "添加文章失败，文章标题已存在");
        }

        Integer integer = postContentService.addPostCategory(categoryId, postContentVo);

        if (integer > 0) {
            postContentService.updatePostsRedisInfo(categoryId);
            return ResponseUtil.ok();
        }
        return ResponseUtil.fail();
    }

    @Log("修改文章")
    @ApiOperation(value = "更改文章")
    @PutMapping("update")
    public Object updatePostContent(@LoginUser Integer userId, @RequestBody String body) {
        Integer postId = JacksonUtil.parseInteger(body, "postId");
        Integer cateId = JacksonUtil.parseInteger(body, "categoryId");

        PostContentVo postContentVo = getBodyIntoPostContentVo(userId, body, "update");
        if (postContentVo == null) {
            return ResponseUtil.fail(803, "修改文章失败，文章标题已存在");
        }
        String originalContent = JacksonUtil.parseString(body, "originalContent");
        boolean unSafe = true;
        if(originalContent != null) {
            try{
                unSafe =wxMaSecCheckService.checkMessage(originalContent);
            }catch (WxErrorException e) {
                return ResponseUtil.fail(202, "添加文章失败，出现违禁词");
            }
        }
        if(!unSafe){
            return ResponseUtil.fail(202, "添加文章失败，出现违禁词");
        }
        if (postContentService.updatePostContent(postId, postContentVo) > 0) {
            postContentService.updatePostsRedisInfo(cateId);
            return ResponseUtil.ok();
        }
        return ResponseUtil.fail();
    }

    @Log("删除文章")
    @ApiOperation(value = "删除文章")
    @DeleteMapping("delete")
    public Object deletePostContent(@LoginUser Integer userId,@RequestBody String body) {
        Integer postId = JacksonUtil.parseInteger(body, "postId");
        Integer cateId = postContentService.findCateIdByPostId(postId);

        Integer i = postContentService.deletePostContentAndCategory(postId);
        if (i > 0) {
            postContentService.updatePostsRedisInfo(cateId);
            //删除文章评论的缓存
            return ResponseUtil.ok();
        }
        return ResponseUtil.fail();
    }

    @ApiOperation("文章详细")
    @GetMapping("/getDetail/{post_id}")
    public Object getPostDetail(@LoginUser Integer userId, @PathVariable("post_id") Integer post_id) {
        Map<String, Object> result = new HashMap<>();
        PostCommentDto postCommentDto = postCommentService.findPostsByPostId(post_id);

        result.put("originalContent", postCommentDto.getOriginalContent());
        result.put("visits", postCommentDto.getVisits());
        result.put("title", postCommentDto.getTitle());
        result.put("switch1",ContentUtils.returnTypeInBoolean(postCommentDto.getDisallowComment()));
        result.put("type", ContentUtils.getType(postCommentDto.getType()));
        result.put("updateTime", postCommentDto.getUpdateTime());
        result.put("createTime", postCommentDto.getCreateTime());
        result.put("baseComment", postCommentDto.getCommentDtoList());
        return ResponseUtil.ok(result);
    }


    /**
     *
     * @param userId
     * @param body
     * @param methodName
     * @return
     */
    public PostContentVo getBodyIntoPostContentVo(Integer userId, String body, String methodName) {
        String title = JacksonUtil.parseString(body, "title");
        boolean type = JacksonUtil.parseBoolean(body, "type");
        boolean allowComment = JacksonUtil.parseBoolean(body, "allowComment");
        String originalContent = JacksonUtil.parseString(body, "originalContent");
        Integer categoryId = JacksonUtil.parseInteger(body, "categoryId");
        Integer postId = JacksonUtil.parseInteger(body, "postId");

        List<String> titles = postContentService.findAllPostsNameByCategoryId(categoryId);
        PostCommentDto dto = postCommentService.findPostsByPostId(postId);
        if (!CollectionUtils.isEmpty(titles)) {
            if (methodName.equals("update")) {
                titles.remove(dto.getTitle());
            }
            if (titles.contains(title)) {
                return null;
            }
        }

        PostContentVo postContentVo = new PostContentVo();
        postContentVo.setCreateFrom(userId);
        postContentVo.setTitle(title);
        if(StringUtils.isEmpty(originalContent)){
            originalContent = " ";
        }
        postContentVo.setOriginalContent(originalContent);
        if (originalContent.length() < 10) {
            postContentVo.setFormatContent(originalContent);
        } else {
            postContentVo.setFormatContent(originalContent.substring(0, 10));
        }

        if (type) {
            postContentVo.setType(1);
        } else {
            postContentVo.setType(2);
        }

        if (allowComment) {
            postContentVo.setDisallowComment(0);
        } else {
            postContentVo.setDisallowComment(1);
        }
        return postContentVo;
    }

}
