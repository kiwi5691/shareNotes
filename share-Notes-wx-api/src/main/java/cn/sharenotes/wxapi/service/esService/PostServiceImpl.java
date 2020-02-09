package cn.sharenotes.wxapi.service.esService;

import cn.sharenotes.core.utils.WxResponseCode;
import cn.sharenotes.db.domain.index.PostsIndex;
import cn.sharenotes.db.mapper.*;
import cn.sharenotes.db.model.dto.PageSearchDto;
import cn.sharenotes.db.model.vo.PostSearchVo;
import cn.sharenotes.db.repository.PostsIndexRepository;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class PostServiceImpl implements PostService{

    @Autowired
    private UserGroupsMapper userGroupsMapper;
    @Autowired
    private CategoriesMapper categoriesMapper;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PostsMapper postsMapper;
    @Autowired
    private PostCategoriesMapper postCategoriesMapper;
    @Autowired
    private PostsIndexRepository postsIndexRepository;
    @Override
    public Page<PostsIndex> findAll(PageSearchDto pageSearchDto) {
        Pageable pageable = PageRequest.of(pageSearchDto.getPage(), pageSearchDto.getPageSize());
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withQuery(QueryBuilders.termQuery("originalContent", pageSearchDto.getSearchKeyword()))
                    .withPageable(pageable);
        Page<PostsIndex> posts =  this.postsIndexRepository.search(queryBuilder.build());
        return posts;
    }

    @Override
    public List<PostSearchVo> postSearchVoTransfer(Integer userId,List<PostsIndex> postsIndices) {
        List<Integer> firendIds = this.filterUnFriends(userId);
        List<PostSearchVo> postSearchVos = new ArrayList<>();
        postsIndices.stream().filter(postsIndex ->  !firendIds.contains(postsIndex.getCreateFrom()));
        for (PostsIndex var1: postsIndices) {
                    PostSearchVo postSearchVo = new PostSearchVo();
                    postSearchVo.setId(var1.getId());
                    postSearchVo.setTitle(var1.getTitle());
                    postSearchVo.setUpdateTime(var1.getUpdateTime());
                    Map<String, Object> result = new HashMap<>();
                    //这里因为Mybatis默认一级缓存，所以不会造成数据IO压力
                    result = userMapper.selectNameAndAvatarById(var1.getCreateFrom());
                    postSearchVo.setAvatar(String.valueOf(result.get("avatar")));
                    postSearchVo.setCreateFrom(String.valueOf(result.get("nickname")));

                    postSearchVo.setCateName(this.constructCateName(var1.getId()));
                    postSearchVos.add(postSearchVo);
            }

        return postSearchVos;
    }

    public List<Integer> filterUnFriends(Integer userId){
        return  userGroupsMapper.selectFrindByUseId(userId);
    }
    @Override
    public Integer accordingPostIdGetUserId(Integer id) {
        return postsMapper.listAllPostsName(id);
    }

    @Override
    public Integer getCatedByPostId(Integer id) {
        Optional<Integer> cateId= Optional.ofNullable(postCategoriesMapper.selectCateidByPostId(id));
        return cateId.orElse(WxResponseCode.errPostId);
    }


    public String constructCateName(Integer id){

        Optional<Integer> cateId = Optional.of(postCategoriesMapper.selectCategeIdByPostId(id));
        if(cateId.isPresent()){
            return categoriesMapper.selectByCateId(cateId.get());
        }else{
            return "404";
        }
    }
}
