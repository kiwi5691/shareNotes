package cn.sharenotes.core.service.Impl;

import cn.sharenotes.core.redis.KeyPrefix.OwnerContentKey;
import cn.sharenotes.core.redis.RedisManager;
import cn.sharenotes.db.domain.*;
import cn.sharenotes.db.mapper.PostCategoriesMapper;
import cn.sharenotes.db.mapper.PostsMapper;
import cn.sharenotes.db.model.dto.PostDTO;
import cn.sharenotes.core.service.PostContentService;
import cn.sharenotes.db.utils.DtoUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
    private RedisManager redisManager;

    @Resource
    private PostCategoriesMapper postCategoriesMapper;

    @Override
    public List<PostDTO> findPostsByUserId(Integer userId, Integer cateId) {


        List<Integer> postIds =null;
        List<PostsWithBLOBs> posts =null;
        List<PostDTO> postDTOS = null;


        postIds = redisManager.get(OwnerContentKey.board, "catePostIds :"+cateId, List.class);
        if(postIds == null) {
            PostCategoriesExample postCategoriesExample = new PostCategoriesExample();
            postCategoriesExample.setOrderByClause("create_time DESC");
            postCategoriesExample.createCriteria().andCategoryIdEqualTo(cateId);
            List<PostCategories> postCategories = postCategoriesMapper.selectByExample(postCategoriesExample);
            postIds = postCategories.stream().map(PostCategories::getPostId).collect(Collectors.toList());
            postIds=Optional.ofNullable(postIds).orElseGet(Collections::emptyList);
            redisManager.set(OwnerContentKey.board, "catePostIds"+cateId, postIds);
        }

        if(!CollectionUtils.isEmpty(postIds)){
              PostsExample postsExample = new PostsExample();
              postsExample.setOrderByClause("update_time DESC");
              postsExample.createCriteria().andCreateFromEqualTo(userId)
                                            .andIdIn(postIds);
              posts =postsMapper.selectByExampleWithBLOBs(postsExample);
              postDTOS = DtoUtils.convertList2List(posts,PostDTO.class);
            return postDTOS;
        }
        postDTOS = DtoUtils.convertList2List(posts,PostDTO.class);
        return postDTOS;
    }
}
