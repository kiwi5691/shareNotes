package cn.sharenotes.core.service;

import cn.sharenotes.db.domain.PostsWithBLOBs;
import cn.sharenotes.db.model.dto.PostDTO;
import cn.sharenotes.db.model.vo.PostContentVo;

import java.util.List;

/**
 * @author 76905
 * @auther kiwi
 * @Date 2019/10/6 12:08
 */
public interface PostContentService {
    List<PostDTO> findPostsByCateId(Integer cateId);

    List<PostDTO> friendFindPostsByCateId(Integer cateId);

    List<PostsWithBLOBs> listPostsWithBLOBs();

    Integer addPostContent(Integer categoryId,PostContentVo postContentVo);

    List<String> findAllPostsNameByCategoryId(Integer categoryId);

    Integer addPostCategory(Integer categoryId,PostContentVo postContentVo);

    Integer updatePostContent(Integer postId,PostContentVo postContentVo);

    Integer deletePostContentAndCategory(Integer postId);

    Integer findCateIdByPostId(Integer postId);

    void updatePostsRedisInfo(Integer categoryId);
}
