package cn.sharenotes.wxapi.web.category;

import cn.sharenotes.db.model.dto.CategoryDTO;
import cn.sharenotes.db.service.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/wx/category")
public class WxCategoryController {

    @Autowired
    CategoriesService categoriesService;

    @PostMapping("/getAll/{userId}")
    public List<CategoryDTO> getAllCategories(@PathVariable("userId") Integer userId){
        return categoriesService.findCategoriesByUserOpenId(userId);
    }
}
