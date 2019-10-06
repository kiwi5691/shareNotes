package cn.sharenotes.core.service;

import cn.sharenotes.db.model.dto.CategoryDTO;
import cn.sharenotes.db.model.vo.CategoryVO;

import java.util.List;

/**
 * @author 76905
 */
public interface CategoriesService {

    List<CategoryDTO> findCategoriesByUserOpenIdWithMenuId(Integer userId,Integer menuId);

    int addCategories(Integer userId,CategoryVO categoryVO);

    List<String> findAllCategoriesNameByUserOpenIdWithMenuId(Integer userId, Integer menuId);

    boolean delectCategorieByMenuId(Integer menuId);

}
