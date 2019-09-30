package cn.sharenotes.db.service.Impl;

import cn.sharenotes.db.dao.CategoriesMapper;
import cn.sharenotes.db.dao.UserMapper;
import cn.sharenotes.db.domain.Categories;
import cn.sharenotes.db.domain.CategoriesExample;
import cn.sharenotes.db.domain.User;
import cn.sharenotes.db.model.dto.CategoryDTO;
import cn.sharenotes.db.service.CategoriesService;
import cn.sharenotes.db.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 76905
 */
@Service
public class CategoriesServiceImpl implements CategoriesService {

    @Autowired
    private CategoriesMapper categoriesMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<CategoryDTO> findCategoriesByUserOpenId(Integer userId,Integer menuId) {
        User user = userMapper.selectByPrimaryKey(userId);
        CategoriesExample categoriesExample = new CategoriesExample();
        CategoriesExample.Criteria criteria = categoriesExample.createCriteria();
        criteria.andSlugNameEqualTo(user.getWeixinOpenid()).andParentIdEqualTo(menuId);
        List<Categories> categories = categoriesMapper.selectByExample(categoriesExample);
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
//                DtoUtils.convertList2List(categories,CategoryDTO.class);
        if (!CollectionUtils.isEmpty(categories)) {
            for (Categories source : categories) {
                CategoryDTO categoryDTO = new CategoryDTO();
                categoryDTO.setName(source.getName());
                categoryDTO.setCreateTime(DateUtil.preciseDate(source.getCreateTime()));
                categoryDTO.setUpdateTime(DateUtil.preciseDate(source.getUpdateTime()));
                categoryDTOS.add(categoryDTO);
            }
        }
        return categoryDTOS;
    }

}
