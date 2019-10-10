package cn.sharenotes.core.service;

import cn.sharenotes.db.model.dto.CategoryDTO;
import cn.sharenotes.db.model.dto.CategoryDetailDTO;
import cn.sharenotes.db.model.vo.CategoryVO;

import java.util.List;

/**
 * @author 76905
 */
public interface CategoriesService {

    List<CategoryDTO> findCategoriesByUserOpenIdWithMenuId(Integer userId,Integer menuId);

    CategoryDetailDTO findCategoriesDetailByCid(Integer userId, Integer categoryId);

    Integer addCategory(Integer userId,CategoryVO categoryVO);

    List<String> findAllCategoryNameByUserOpenIdWithMenuId(Integer userId, Integer menuId);

    Integer deleteCategoryByCategoryId(Integer menuId,Integer categoryId);

    void updateCategoriesRedisInfo(Integer userId, Integer menuId);

    Integer updateCategoryByCategoryId(Integer categoryId,CategoryVO categoryVO);

}
