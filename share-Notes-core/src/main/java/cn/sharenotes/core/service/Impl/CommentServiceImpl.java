package cn.sharenotes.core.service.Impl;

import cn.sharenotes.core.redis.RedisManager;
import cn.sharenotes.core.service.CommentService;
import cn.sharenotes.db.domain.Comments;
import cn.sharenotes.db.domain.User;
import cn.sharenotes.db.domain.*;
import cn.sharenotes.db.mapper.CommentsMapper;
import cn.sharenotes.db.mapper.PostsMapper;
import cn.sharenotes.db.mapper.SysMsgMapper;
import cn.sharenotes.db.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;

@Service
public class CommentServiceImpl implements CommentService {
    @Resource
    private CommentsMapper commentsMapper;
    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisManager redisManager;

    @Value("OWNER_COMMENT_BY_POSTID")
    private String OWNER_COMMENT_BY_POSTID;

    @Resource
    private PostsMapper postsMapper;
    @Resource
    private SysMsgMapper sysMsgMapper;
    @Override
    public Integer addComment(int userid, int post_id,String content, Boolean isanonymous) {

        Date date = new Date();

        User user = userMapper.selectByPrimaryKey(userid);

        int anony =0;
        if(isanonymous){
            anony = 0;
        }else {
            anony =1;
        }
        Posts posts = postsMapper.selectByPrimaryKey(post_id);
        Comments comments = new Comments(0,new Timestamp(date.getTime()),new Timestamp(date.getTime()),user.getNickname(),content, anony,post_id,1,0,userid);

        redisManager.set(OWNER_COMMENT_BY_POSTID+":postId:"+ post_id, comments);

        SysMsg sysMsg = new SysMsg(userid,comments.getUserId(),posts.getTitle(),1,comments.getCreateTime());
        sysMsgMapper.insert(sysMsg);
        return commentsMapper.insert(comments);
    }

    @Override
    public Integer delectComment(int id) {

        redisManager.del(OWNER_COMMENT_BY_POSTID+":postId:"+ id);

        return commentsMapper.deleteByPrimaryKey((long) id);
    }
}
