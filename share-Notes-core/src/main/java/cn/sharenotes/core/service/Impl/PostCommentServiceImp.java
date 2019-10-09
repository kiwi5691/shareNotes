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
    private RedisManager redisManager;
    @Resource
    private UserMapper userMapper;

    @Override
    public PostCommentDto findPostsByPostId(Integer postId) {
        PostCommentDto postCommentDto = new PostCommentDto();
        PostsWithBLOBs postsWithBLOBs = postsMapper.selectByPrimaryKey(postId);

        if(postsWithBLOBs==null){
            return null;
        }
        DtoUtils.copyProperties( postsWithBLOBs,postCommentDto);
        if(postsWithBLOBs.getDisallowComment().equals(ContentBase.ALLOWACCESS.getValue())){
            postCommentDto.setNotallowComment(true);
        }else {
            postCommentDto.setNotallowComment(false);
        }
        List<Comments> commentsList = commentsMapper.selectByPostId(postId);

        if(CollectionUtils.isEmpty(commentsList)){
            return postCommentDto;
        }
        List<CommentDto> commentDtoList = new ArrayList<>();

        for (Comments comments:
                commentsList ) {
            User user = userMapper.selectByPrimaryKey(comments.getUserId());

            CommentDto commentDto = new CommentDto(comments,user);
            commentDtoList.add(commentDto);
        }

        postCommentDto.setCommentDtoList(commentDtoList);
        return postCommentDto;
    }
}