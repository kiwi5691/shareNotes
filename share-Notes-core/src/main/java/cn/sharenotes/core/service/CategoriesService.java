package cn.sharenotes.core.service;

import cn.sharenotes.db.model.dto.CategoryDTO;
import cn.sharenotes.db.model.vo.CategoryVO;

import java.util.List;

/**
 * @author 76905
 */
public interface CategoriesService {

    List<CategoryDTO> findCategoriesByUserOpenId(Integer userId,Integer menuId);

    int addCategories(Integer userId,CategoryVO categoryVO);

    List<String> findAllCategoriesName();

}
