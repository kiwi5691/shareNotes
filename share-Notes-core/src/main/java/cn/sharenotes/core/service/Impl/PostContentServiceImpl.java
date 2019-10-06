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
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${OWNER_POSTS_BY_CATID}")
    private String OWNER_POSTS_BY_CATID;
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
            String Key=OwnerContentKey.board.getPrefix();
            postDTOS = (List<PostDTO>) redisManager.getList(OWNER_POSTS_BY_CATID+":"+"posts :"+cateId);
            if(postDTOS==null) {
                PostsExample postsExample = new PostsExample();
                postsExample.setOrderByClause("update_time DESC");
                postsExample.createCriteria().andCreateFromEqualTo(userId)
                        .andIdIn(postIds);
                posts = postsMapper.selectByExampleWithBLOBs(postsExample);
                postDTOS = DtoUtils.convertList2List(posts, PostDTO.class);
                postDTOS = Optional.ofNullable(postDTOS).orElseGet(Collections::emptyList);
                redisManager.setList( OWNER_POSTS_BY_CATID+":"+"posts :"+cateId, postDTOS);
            }
            return postDTOS;
        }

        postDTOS = DtoUtils.convertList2List(posts,PostDTO.class);
        return postDTOS;
    }
}
