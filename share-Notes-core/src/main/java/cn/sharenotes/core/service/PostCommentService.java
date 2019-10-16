package cn.sharenotes.core.service;

import cn.sharenotes.db.model.dto.CommentDto;
import cn.sharenotes.db.model.dto.PostCommentDto;

import java.util.List;

/**
 * @author hu
 * @Date 2019/10/8 20:08
 */
public interface PostCommentService {
    /**
     * 通过目录id获取内容和评论
     *
     * @return 内容和评论
     */
    public  PostCommentDto findPostsByPostId(Integer postId);

    /**
     * 通过目录id获取评论
     *
     * @return 评论
     */
    public List<CommentDto> findCommentByPostId(Integer postId);

    public int IncrVisit(Integer post_id);
}
