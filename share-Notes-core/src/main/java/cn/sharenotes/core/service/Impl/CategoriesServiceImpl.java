package cn.sharenotes.core.service.Impl;

import cn.sharenotes.core.redis.RedisManager;
import cn.sharenotes.core.service.CategoriesService;
import cn.sharenotes.db.domain.Categories;
import cn.sharenotes.db.domain.CategoriesExample;
import cn.sharenotes.db.domain.User;
import cn.sharenotes.db.mapper.CategoriesMapper;
import cn.sharenotes.db.mapper.UserMapper;
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

    @Autowired
    private UserMapper userMapper;

    @Resource
    private RedisManager redisManager;
    @Value("${OWNER_MENUID}")
    private String OWNER_MENUID;

    @Override
    public List<CategoryDTO> findCategoriesByUserOpenIdWithMenuId(Integer userId,Integer menuId) {
        List<CategoryDTO> categoryDTOS =null;
        List<Categories> categories =null;

        categoryDTOS = (List<CategoryDTO>) redisManager.getList(OWNER_MENUID+":"+"menuIds :"+menuId +"userId:"+userId);
        if(CollectionUtils.isEmpty(categoryDTOS)) {
            User user = userMapper.selectByPrimaryKey(userId);
            CategoriesExample categoriesExample = new CategoriesExample();
            CategoriesExample.Criteria criteria = categoriesExample.createCriteria();
            criteria.andSlugNameEqualTo(user.getWeixinOpenid()).andParentIdEqualTo(menuId);
            categories = categoriesMapper.selectByExample(categoriesExample);
            categoryDTOS = DtoUtils.convertList2List(categories, CategoryDTO.class);
            if(CollectionUtils.isEmpty(categoryDTOS)){
                return categoryDTOS;
            }
            categoryDTOS= Optional.ofNullable(categoryDTOS).orElseGet(Collections::emptyList);
            redisManager.setList(OWNER_MENUID+":"+"menuIds :"+menuId +"userId:"+userId, categoryDTOS);
        }
        return categoryDTOS;
    }

    @Override
    public CategoryDetailDTO findCategoriesDetailByCid(Integer userId, Integer categoryId) {
        List<CategoryDTO> puCategoryDTOS =null;
        List<CategoryDTO> prCategoryDTOS =null;
        CategoryDTO outCategoryDTO =null;

        puCategoryDTOS = (List<CategoryDTO>) redisManager.getList(OWNER_MENUID+":"+"menuIds :"+1 +"userId:"+userId);
        prCategoryDTOS = (List<CategoryDTO>) redisManager.getList(OWNER_MENUID+":"+"menuIds :"+2 +"userId:"+userId);
        if(!CollectionUtils.isEmpty(puCategoryDTOS)) {
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
    public int addCategory(Integer userId,CategoryVO categoryVO) {
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
        for(CategoryDTO categoryDTO : categoryDTOS){
            strings.add(categoryDTO.getName());
        }
        return strings;
    }

    @Override
    public boolean deleteCategoryByCategoryId(Integer categoryId) {
        categoriesMapper.deleteByPrimaryKey(categoryId);
        return true;
    }

    @Override
    public void updateCategoriesRedisInfo(Integer userId, Integer menuId) {
        List<CategoryDTO> categoryDTOS =null;
        List<Categories> categories =null;

         redisManager.del(Collections.singleton(OWNER_MENUID + ":" + "menuIds :" + menuId + "userId:" + userId));
          User user = userMapper.selectByPrimaryKey(userId);
          CategoriesExample categoriesExample = new CategoriesExample();
          CategoriesExample.Criteria criteria = categoriesExample.createCriteria();
          criteria.andSlugNameEqualTo(user.getWeixinOpenid()).andParentIdEqualTo(menuId);
          categories = categoriesMapper.selectByExample(categoriesExample);
          categoryDTOS = DtoUtils.convertList2List(categories, CategoryDTO.class);

          categoryDTOS= Optional.ofNullable(categoryDTOS).orElseGet(Collections::emptyList);
          redisManager.setList(OWNER_MENUID+":"+"menuIds :"+menuId +"userId:"+userId, categoryDTOS);
    }

    @Override
    public int updateCategoryByCategoryId(Integer categoryId,CategoryVO categoryVO) {
        Categories categories = new Categories();
        DtoUtils.copyProperties(categoryVO,categories);
        categories.setId(categoryId);
        categories.setUpdateTime(new Date());
        return categoriesMapper.updateByPrimaryKeySelective(categories);
    }


}
