package cn.sharenotes.core.service.Impl;

import cn.sharenotes.core.aop.annotation.IndexMethod;
import cn.sharenotes.core.aop.annotation.MethodType;
import cn.sharenotes.core.aop.cache.IndexThreadLocal;
import cn.sharenotes.core.redis.KeyPrefix.OwnerContentKey;
import cn.sharenotes.core.redis.RedisManager;
import cn.sharenotes.core.service.PostContentService;
import cn.sharenotes.db.domain.PostCategories;
import cn.sharenotes.db.domain.PostCategoriesExample;
import cn.sharenotes.db.domain.PostsExample;
import cn.sharenotes.db.domain.PostsWithBLOBs;
import cn.sharenotes.db.domain.index.PostsIndex;
import cn.sharenotes.db.mapper.CommentsMapper;
import cn.sharenotes.db.mapper.PostCategoriesMapper;
import cn.sharenotes.db.mapper.PostsMapper;
import cn.sharenotes.db.model.dto.PostDTO;
import cn.sharenotes.db.model.vo.PostContentVo;
import cn.sharenotes.db.repository.PostsIndexRepository;
import cn.sharenotes.db.utils.DtoUtils;
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
@Service
public class PostContentServiceImpl implements PostContentService {

    @Resource
    private PostsMapper postsMapper;

    @Resource
    private RedisManager redisManager;

    @Resource
    private CommentsMapper commentsMapper;

    @Value("${OWNER_POSTS_BY_CATID}")
    private String OWNER_POSTS_BY_CATID;

    @Resource
    private PostCategoriesMapper postCategoriesMapper;

    @Override
    public List<PostDTO> findPostsByCateId(Integer cateId) {
        List<PostDTO> postDTOS = null;
        postDTOS = (List<PostDTO>) redisManager.getList(OWNER_POSTS_BY_CATID + ":cate :" + cateId);
        if(CollectionUtils.isEmpty(postDTOS)){
            postDTOS = getAllPostIdsByCateId(cateId);
            if(CollectionUtils.isEmpty(postDTOS)){
                return null;
            }
        }
        return postDTOS;
    }

    @Override
    public List<PostDTO> friendFindPostsByCateId(Integer cateId) {
        List<PostDTO> postDTOS = null;
        if(CollectionUtils.isEmpty(postDTOS)){
            postDTOS = getAllPostIdsByCateId(cateId);
            if(CollectionUtils.isEmpty(postDTOS)){
                return null;
            }
        }
        return postDTOS;
    }

    @Override
    public List<PostsWithBLOBs> listPostsWithBLOBs() {
        List<PostsWithBLOBs> postsWithBLOBs = postsMapper.listAllPosts();;
        return postsWithBLOBs;
    }

    @IndexMethod(method = MethodType.SAVE)
    @Override
    public Integer addPostContent(Integer categoryId, PostContentVo postContentVo) {
        PostsWithBLOBs posts = new PostsWithBLOBs();
        DtoUtils.copyProperties(postContentVo, posts);
        posts.setCreateTime(new Date());
        posts.setUpdateTime(new Date());
        posts.setVisits((long) 0);
        postsMapper.insert(posts);
        PostsIndex postsIndex = new PostsIndex();
        DtoUtils.copyProperties(posts, postsIndex);
        IndexThreadLocal.set(postsIndex);
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

    @IndexMethod(method = MethodType.UPDATE)
    @Override
    public Integer updatePostContent(Integer postId, PostContentVo postContentVo) {
        PostsWithBLOBs posts = new PostsWithBLOBs();
        DtoUtils.copyProperties(postContentVo,posts);
        posts.setId(postId);
        posts.setUpdateTime(new Date());
        posts.setEditTime(new Date());
        PostsIndex postsIndex = new PostsIndex();
        DtoUtils.copyProperties(posts, postsIndex);
        IndexThreadLocal.set(postsIndex);
        return postsMapper.updateByPrimaryKeySelective(posts);
    }

    @IndexMethod(method = MethodType.DELETE)
    @Override
    public Integer deletePostContentAndCategory(Integer postId) {
        //获取实体 start
        PostsExample postsExample = new PostsExample();
        PostsExample.Criteria criteria1 = postsExample.createCriteria();
        criteria1.andIdEqualTo(postId);

        List<PostsWithBLOBs> postsWithBLOBs = postsMapper.selectByExampleWithBLOBs(postsExample);
        Optional<PostsWithBLOBs> optionalPostsIndex = Optional.ofNullable(postsWithBLOBs.get(0));
        PostsIndex postsIndex = new PostsIndex();
        optionalPostsIndex.ifPresent(postsWithBLOBs1 -> {
            DtoUtils.copyProperties(postsWithBLOBs1, postsIndex);
            IndexThreadLocal.set(postsIndex);
        });
        //end

        postsMapper.deleteByPrimaryKey(postId);

        commentsMapper.deleteByPostId(postId);
        PostCategoriesExample postCategoriesExample = new PostCategoriesExample();
        PostCategoriesExample.Criteria criteria = postCategoriesExample.createCriteria();
        criteria.andPostIdEqualTo(postId);
        return postCategoriesMapper.deleteByExample(postCategoriesExample);
    }

    @Override
    public Integer findCateIdByPostId(Integer postId) {
        PostCategoriesExample example = new PostCategoriesExample();
        PostCategoriesExample.Criteria criteria = example.createCriteria();
        criteria.andPostIdEqualTo(postId);
        List<PostCategories> postCategories = postCategoriesMapper.selectByExample(example);
        return postCategories.get(0).getCategoryId();
    }

    @Override
    public void updatePostsRedisInfo(Integer categoryId) {
        List<PostDTO> postDTOS = null;
        redisManager.del(OWNER_POSTS_BY_CATID + ":cate :" + categoryId);
        postDTOS = getAllPostIdsByCateId(categoryId);
        redisManager.setList(OWNER_POSTS_BY_CATID + ":cate :" + categoryId,postDTOS);
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
        redisManager.setList(OWNER_POSTS_BY_CATID + ":cate :" + cateId, postDTOS);
        return postDTOS;
    }
}
