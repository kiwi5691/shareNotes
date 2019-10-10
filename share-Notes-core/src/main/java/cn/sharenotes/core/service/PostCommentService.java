package cn.sharenotes.core.service;

import cn.sharenotes.db.model.dto.PostCommentDto;

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

    public int IncrVisit(Integer post_id);
}
