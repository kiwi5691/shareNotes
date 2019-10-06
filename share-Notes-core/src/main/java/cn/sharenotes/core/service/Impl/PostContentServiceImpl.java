package cn.sharenotes.core.service.Impl;

import cn.sharenotes.db.domain.*;
import cn.sharenotes.db.mapper.PostCategoriesMapper;
import cn.sharenotes.db.mapper.PostsMapper;
import cn.sharenotes.db.model.dto.PostDTO;
import cn.sharenotes.core.service.PostContentService;
import cn.sharenotes.db.utils.DtoUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @auther kiwi
 * @Date 2019/10/6 12:08
 */
@Slf4j
@Service
public class PostContentServiceImpl implements PostContentService {

    @Resource
    private PostsMapper postsMapper;

    @Resource
    private PostCategoriesMapper postCategoriesMapper;

    @Override
    public List<PostDTO> findPostsByUserId(Integer userId, Integer cateId) {

        //// TODO: 2019/10/6 添加redis 存储 从cate获取postids

        PostCategoriesExample postCategoriesExample = new PostCategoriesExample();
        postCategoriesExample.setOrderByClause("create_time DESC");
        postCategoriesExample.createCriteria().andCategoryIdEqualTo(cateId);
        List<PostCategories> postCategories = postCategoriesMapper.selectByExample(postCategoriesExample);
        List<Integer> postIds = postCategories.stream().map(PostCategories::getPostId).collect(Collectors.toList());

        log.info(postIds.toString());
        PostsExample postsExample = new PostsExample();
        postsExample.setOrderByClause("update_time DESC");
        postsExample.createCriteria().andCreateFromEqualTo(userId).
                andIdIn(postIds);
        List<PostsWithBLOBs> posts =postsMapper.selectByExampleWithBLOBs(postsExample);
        List<PostDTO> postDTOS = DtoUtils.convertList2List(posts,PostDTO.class);

        return postDTOS;
    }
}
