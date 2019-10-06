package cn.sharenotes.core.service;

import cn.sharenotes.db.model.dto.PostDTO;

import java.util.List;

/**
 * @auther kiwi
 * @Date 2019/10/6 12:08
 */
public interface PostContentService {
    public List<PostDTO> findPostsByUserId(Integer userId, Integer cateId);

}
