package cn.sharenotes.core.service.Impl;

import cn.sharenotes.core.enums.ContentBase;
import cn.sharenotes.core.service.CommentService;
import cn.sharenotes.core.utils.DateTimeUtil;
import cn.sharenotes.db.domain.Comments;
import cn.sharenotes.db.domain.User;
import cn.sharenotes.db.mapper.CommentsMapper;
import cn.sharenotes.db.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class CommentServiceImpl implements CommentService {
    @Resource
    private CommentsMapper commentsMapper;
    @Resource
    private UserMapper userMapper;
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

        Comments comments = new Comments(0,new Timestamp(date.getTime()),new Timestamp(date.getTime()),user.getNickname(),content, anony,post_id,1,0,userid);

        return commentsMapper.insert(comments);
    }

    @Override
    public Integer delectComment(int id) {


        return commentsMapper.deleteByPrimaryKey((long) id);
    }
}
