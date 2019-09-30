package cn.sharenotes.db.service;

import cn.sharenotes.db.model.dto.CategoryDTO;

import java.util.List;

/**
 * @author 76905
 */
public interface CategoriesService {

    public List<CategoryDTO> findCategoriesByUserOpenId(Integer userId);

}
