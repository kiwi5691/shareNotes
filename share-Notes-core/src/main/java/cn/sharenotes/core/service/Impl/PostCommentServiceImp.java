package cn.sharenotes.core.service.Impl;

import cn.sharenotes.core.enums.ContentBase;
import cn.sharenotes.core.redis.RedisManager;
import cn.sharenotes.core.service.PostCommentService;
import cn.sharenotes.db.domain.Comments;
import cn.sharenotes.db.domain.PostsWithBLOBs;
import cn.sharenotes.db.domain.User;
import cn.sharenotes.db.mapper.CommentsMapper;
import cn.sharenotes.db.mapper.PostsMapper;
import cn.sharenotes.db.mapper.UserMapper;
import cn.sharenotes.db.model.dto.CommentDto;
import cn.sharenotes.db.model.dto.PostCommentDto;
import cn.sharenotes.db.utils.DtoUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
/**
 * @author 76905
 */
@Service
@Slf4j
public class PostCommentServiceImp implements PostCommentService {
    @Resource
    private PostsMapper postsMapper;
    @Resource
    private CommentsMapper commentsMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisManager redisManager;

    @Value("OWNER_COMMENT_BY_POSTID")
    private String OWNER_COMMENT_BY_POSTID;

    @Override
    public PostCommentDto findPostsByPostId(Integer postId) {
        List<Comments> commentsList =null;

        PostCommentDto postCommentDto = new PostCommentDto();

        PostsWithBLOBs postsWithBLOBs = postsMapper.selectByPrimaryKey(postId);

        if(postsWithBLOBs==null){
            return null;
        }

        DtoUtils.copyProperties( postsWithBLOBs,postCommentDto);

        if(postsWithBLOBs.getDisallowComment().equals(ContentBase.ALLOWACCESS.getValue())){
            postCommentDto.setDisallowComment(0);
        }else {
            postCommentDto.setDisallowComment(1);
        }

        commentsList = (List<Comments>) redisManager.getList(OWNER_COMMENT_BY_POSTID + ":postId:" + postId);

        if(CollectionUtils.isEmpty(commentsList)){
            commentsList = commentsMapper.selectByPostId(postId);
            redisManager.setList(OWNER_COMMENT_BY_POSTID+":postId:"+postId,commentsList);
        }

        if(CollectionUtils.isEmpty(commentsList)){
            return postCommentDto;
        }

        List<CommentDto> commentDtoList = new ArrayList<>();

        for (Comments comments: commentsList ) {

            User user = userMapper.selectByPrimaryKey(comments.getUserId());

            CommentDto commentDto = new CommentDto(comments,user);
            commentDtoList.add(commentDto);
        }

        postCommentDto.setCommentDtoList(commentDtoList);

        return postCommentDto;
    }

    @Override
    public int IncrVisit(Integer post_id) {
       return postsMapper.incrVisit(post_id);
    }
}
