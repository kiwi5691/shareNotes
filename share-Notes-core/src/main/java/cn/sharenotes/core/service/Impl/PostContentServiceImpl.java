package cn.sharenotes.core.service.Impl;

import cn.sharenotes.core.redis.KeyPrefix.OwnerContentKey;
import cn.sharenotes.core.redis.RedisManager;
import cn.sharenotes.db.domain.*;
import cn.sharenotes.db.mapper.PostCategoriesMapper;
import cn.sharenotes.db.mapper.PostsMapper;
import cn.sharenotes.db.model.dto.PostDTO;
import cn.sharenotes.core.service.PostContentService;
import cn.sharenotes.db.model.vo.PostContentVo;
import cn.sharenotes.db.utils.DtoUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 76905
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
    public List<PostDTO> findPostsByCateId(Integer cateId) {
        List<PostDTO> postDTOS = null;
        postDTOS = (List<PostDTO>) redisManager.getList(OWNER_POSTS_BY_CATID + ":" + "posts :" + cateId);
        if(CollectionUtils.isEmpty(postDTOS)){
            postDTOS = getAllPostIdsByCateId(cateId);
            if(CollectionUtils.isEmpty(postDTOS)){
                return null;
            }
        }
        return postDTOS;
    }

    @Override
    public Integer addPostContent(Integer categoryId, PostContentVo postContentVo) {
        PostsWithBLOBs posts = new PostsWithBLOBs();
        DtoUtils.copyProperties(postContentVo, posts);
        posts.setCreateTime(new Date());
        posts.setVisits((long) 0);
        posts.setDisallowComment(0);
        postsMapper.insert(posts);
        return posts.getId();
    }

    @Override
    public Integer addPostCategory(Integer categoryId,PostContentVo postContentVo) {
        Integer integer = addPostContent(categoryId, postContentVo);
        PostCategories postCategories = new PostCategories();
        postCategories.setCreateTime(new Date());
        postCategories.setPostId(integer);
        postCategories.setCategoryId(categoryId);
        return postCategoriesMapper.insert(postCategories);
    }

    @Override
    public Integer deletePostContentAndCategory(Integer postId) {
        postsMapper.deleteByPrimaryKey(postId);

        PostCategoriesExample postCategoriesExample = new PostCategoriesExample();
        PostCategoriesExample.Criteria criteria = postCategoriesExample.createCriteria();
        criteria.andPostIdEqualTo(postId);
        return postCategoriesMapper.deleteByExample(postCategoriesExample);
    }

    @Override
    public List<String> findAllPostsNameByCategoryId(Integer categoryId) {
        List<String> strings = new ArrayList<>();
        List<PostDTO> postDTOS = findPostsByCateId(categoryId);
        if(CollectionUtils.isEmpty(postDTOS)){
            return null;
        }
        for (PostDTO postDTO : postDTOS) {
            strings.add(postDTO.getTitle());
        }
        return strings;
    }

    public List<PostDTO> getAllPostIdsByCateId(Integer cateId) {
        List<Integer> postIds = null;
        List<PostsWithBLOBs> posts = null;
        List<PostDTO> postDTOS = null;

        postIds = redisManager.get(OwnerContentKey.board, "catePostIds :" + cateId, List.class);

        if (CollectionUtils.isEmpty(postIds)) {
            PostCategoriesExample postCategoriesExample = new PostCategoriesExample();
            postCategoriesExample.setOrderByClause("create_time DESC");
            postCategoriesExample.createCriteria().andCategoryIdEqualTo(cateId);
            List<PostCategories> postCategories = postCategoriesMapper.selectByExample(postCategoriesExample);
            if(CollectionUtils.isEmpty(postCategories)){
                return null;
            }
            postIds = postCategories.stream().map(PostCategories::getPostId).collect(Collectors.toList());
            postIds = Optional.ofNullable(postIds).orElseGet(Collections::emptyList);

            redisManager.set(OwnerContentKey.board, "catePostIds" + cateId, postIds);
        }

        PostsExample postsExample = new PostsExample();
        postsExample.setOrderByClause("update_time DESC");
        postsExample.createCriteria().andIdIn(postIds);
        posts = postsMapper.selectByExampleWithBLOBs(postsExample);
        postDTOS = DtoUtils.convertList2List(posts, PostDTO.class);
        postDTOS = Optional.ofNullable(postDTOS).orElseGet(Collections::emptyList);
        redisManager.setList(OWNER_POSTS_BY_CATID + ":" + "posts :" + cateId, postDTOS);
        return postDTOS;
    }
}
