package cn.sharenotes.core.service.Impl;

import cn.sharenotes.core.redis.RedisManager;
import cn.sharenotes.core.service.CategoriesService;
import cn.sharenotes.db.domain.Categories;
import cn.sharenotes.db.domain.CategoriesExample;
import cn.sharenotes.db.domain.User;
import cn.sharenotes.db.mapper.*;
import cn.sharenotes.db.model.dto.CategoryDTO;
import cn.sharenotes.db.model.dto.CategoryDetailDTO;
import cn.sharenotes.db.model.vo.CategoryVO;
import cn.sharenotes.db.utils.DtoUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author 76905
 */
@Slf4j
@Service
public class CategoriesServiceImpl implements CategoriesService {

    @Autowired
    private CategoriesMapper categoriesMapper;
    @Resource
    private PostCategoriesMapper postCategoriesMapper;
    @Autowired
    private UserMapper userMapper;
    @Resource
    private PostsMapper postsMapper;
    @Resource
    private CommentsMapper commentsMapper;
    @Resource
    private RedisManager redisManager;

    @Value("${OWNER_MENUID}")
    private String OWNER_MENUID;


    @Override
    public List<CategoryDTO> findCategoriesByUserOpenIdWithMenuId(Integer userId,Integer menuId) {
        List<CategoryDTO> categoryDTOS =null;

        categoryDTOS = (List<CategoryDTO>) redisManager.getList(OWNER_MENUID+":menuIds :"+menuId +"userId:"+userId);
        if(CollectionUtils.isEmpty(categoryDTOS)) {
            categoryDTOS = getCategoryDTO(userId,menuId);
            if(CollectionUtils.isEmpty(categoryDTOS)){
                return null;
            }
        }
        return categoryDTOS;
    }

    @Override
    public List<CategoryDTO> friendFindCategoriesByUserOpenIdWithMenuId(Integer userId, Integer menuId) {
        List<CategoryDTO> categoryDTOS =null;

        if(CollectionUtils.isEmpty(categoryDTOS)) {
            categoryDTOS = getCategoryDTO(userId,menuId);
            if(CollectionUtils.isEmpty(categoryDTOS)){
                return null;
            }
        }
        return categoryDTOS;
    }

    @Override
    public CategoryDetailDTO findCategoriesDetailByCid(Integer userId, Integer categoryId) {
        List<CategoryDTO> puCategoryDTOS =null;
        List<CategoryDTO> prCategoryDTOS =null;
        CategoryDTO outCategoryDTO =null;
        final Boolean[] IsInPu = {true};
        puCategoryDTOS = (List<CategoryDTO>) redisManager.getList(OWNER_MENUID+":menuIds :"+1 +"userId:"+userId);
        prCategoryDTOS = (List<CategoryDTO>) redisManager.getList(OWNER_MENUID+":menuIds :"+2 +"userId:"+userId);


        if(!CollectionUtils.isEmpty(puCategoryDTOS)) {
            puCategoryDTOS.stream().forEach(categoryDTO->{
                if(categoryDTO.getId().equals(categoryId)){
                    IsInPu[0] =true;
                }
            });
        }
        if (!CollectionUtils.isEmpty(prCategoryDTOS)){
            prCategoryDTOS.stream().forEach(categoryDTO->{
                if(categoryDTO.getId().equals(categoryId)){
                    IsInPu[0] =false;
                }
            });
        }

        if(IsInPu[0]){
            outCategoryDTO=puCategoryDTOS.stream().filter(categoryDTO->categoryDTO.getId().equals(categoryId)).findAny().get();
            CategoryDetailDTO categoryDetailDTO =new CategoryDetailDTO();
            BeanUtils.copyProperties(outCategoryDTO,categoryDetailDTO);
            categoryDetailDTO.setMenuId("1");
            return  categoryDetailDTO;
        }else {
            outCategoryDTO=prCategoryDTOS.stream().filter(categoryDTO->categoryDTO.getId().equals(categoryId)).findAny().get();
            CategoryDetailDTO categoryDetailDTO =new CategoryDetailDTO();
            BeanUtils.copyProperties(outCategoryDTO,categoryDetailDTO   );
            categoryDetailDTO.setMenuId("2");
            return  categoryDetailDTO;
        }
    }

    @Override
    public Integer addCategory(Integer userId,CategoryVO categoryVO) {
        User user = userMapper.selectByPrimaryKey(userId);
        Categories categories = new Categories();
        categoryVO.setSlugName(user.getWeixinOpenid());
        DtoUtils.copyProperties(categoryVO,categories);
        categories.setCreateTime(new Date());
        categories.setUpdateTime(new Date());
        return categoriesMapper.insert(categories);
    }

    @Override
    public List<String> findAllCategoryNameByUserOpenIdWithMenuId(Integer userId, Integer menuId) {
        List<String> strings = new ArrayList<>();
        List<CategoryDTO> categoryDTOS = findCategoriesByUserOpenIdWithMenuId(userId, menuId);
        if(CollectionUtils.isEmpty(categoryDTOS)){
            return null;
        }
        for(CategoryDTO categoryDTO : categoryDTOS){
            strings.add(categoryDTO.getName());
        }
        return strings;
    }

    @Override
    public Integer deleteCategoryByCategoryId(Integer menuId,Integer categoryId) {
        List<Integer> postlist = postCategoriesMapper.selectPostidByCateid(categoryId);
        if(!CollectionUtils.isEmpty(postlist)){
            for (Integer postid: postlist) {
                postsMapper.deleteByPrimaryKey(postid);
                commentsMapper.deleteByPostId(postid);
            }
        }
        postCategoriesMapper.deleteByCategories(categoryId);

        return categoriesMapper.deleteByPrimaryKey(categoryId);
    }

    @Override
    public void updateCategoriesRedisInfo(Integer userId, Integer menuId) {
        List<CategoryDTO> categoryDTOS =null;

        redisManager.del(OWNER_MENUID + ":menuIds :" + menuId + "userId:" + userId);
        categoryDTOS = getCategoryDTO(userId, menuId);
        categoryDTOS= Optional.ofNullable(categoryDTOS).orElseGet(Collections::emptyList);
        redisManager.setList(OWNER_MENUID+":menuIds :"+menuId +"userId:"+userId, categoryDTOS);
    }

    @Override
    public Integer updateCategoryByCategoryId(Integer categoryId,CategoryVO categoryVO) {
        Categories categories = new Categories();
        DtoUtils.copyProperties(categoryVO,categories);
        categories.setId(categoryId);
        categories.setUpdateTime(new Date());
        return categoriesMapper.updateByPrimaryKeySelective(categories);
    }

    public List<CategoryDTO> getCategoryDTO(Integer userId, Integer menuId) {
        User user = userMapper.selectByPrimaryKey(userId);
        CategoriesExample categoriesExample = new CategoriesExample();
        CategoriesExample.Criteria criteria = categoriesExample.createCriteria();
        criteria.andSlugNameEqualTo(user.getWeixinOpenid()).andParentIdEqualTo(menuId);
        List<Categories> categories = categoriesMapper.selectByExample(categoriesExample);
        List<CategoryDTO> categoryDTOS = DtoUtils.convertList2List(categories, CategoryDTO.class);
        categoryDTOS= Optional.ofNullable(categoryDTOS).orElseGet(Collections::emptyList);
        redisManager.setList(OWNER_MENUID+":menuIds :"+menuId +"userId:"+userId, categoryDTOS);
        return categoryDTOS;
    }

}
