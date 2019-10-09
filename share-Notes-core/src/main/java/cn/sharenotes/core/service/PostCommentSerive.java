package cn.sharenotes.core.service;

import cn.sharenotes.db.model.dto.PostCommentDto;
import cn.sharenotes.db.model.dto.PostDTO;

import java.util.List;

/**
 * @author hu
 * @Date 2019/10/8 20:08
 */
public interface PostCommentSerive {
    /**
     * 通过目录id获取内容和评论
     *
     * @return 内容和评论
     */
    public  PostCommentDto findPostsByPostId(Integer postId);
}
