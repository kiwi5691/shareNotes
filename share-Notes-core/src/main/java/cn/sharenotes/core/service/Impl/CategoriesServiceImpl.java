package cn.sharenotes.core.service.Impl;

import cn.sharenotes.core.service.CategoriesService;
import cn.sharenotes.db.domain.Categories;
import cn.sharenotes.db.domain.CategoriesExample;
import cn.sharenotes.db.domain.User;
import cn.sharenotes.db.mapper.CategoriesMapper;
import cn.sharenotes.db.mapper.UserMapper;
import cn.sharenotes.db.model.dto.CategoryDTO;
import cn.sharenotes.db.model.vo.CategoryVO;
import cn.sharenotes.db.utils.DtoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
        List<CategoryDTO> categoryDTOS = DtoUtils.convertList2List(categories,CategoryDTO.class);
        return categoryDTOS;
    }

    @Override
    public int addCategories(Integer userId,CategoryVO categoryVO) {
        User user = userMapper.selectByPrimaryKey(userId);
        Categories categories = new Categories();
        categoryVO.setSlugName(user.getWeixinOpenid());
        DtoUtils.copyProperties(categoryVO,categories);
        categories.setCreateTime(new Date());
        categories.setUpdateTime(new Date());
        return categoriesMapper.insert(categories);
    }

    @Override
    public List<String> findAllCategoriesName() {
        return categoriesMapper.selectAllName();
    }

}
