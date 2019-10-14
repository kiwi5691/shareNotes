package cn.sharenotes.core.service;

import org.springframework.transaction.annotation.Transactional;

/**
 * @author hu
 * @Date 2019/10/9 16:08
 */
public interface CommentService {
    /**
     * 增加评论
     *
     * @return
     */
    public Integer addComment(int userid,int post_id,String content,Boolean isanonymous);
    /**
     * 删除评论
     *
     * @return
     */
    public Integer delectComment(int id);
}
