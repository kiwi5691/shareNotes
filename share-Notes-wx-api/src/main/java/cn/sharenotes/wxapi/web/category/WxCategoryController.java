package cn.sharenotes.wxapi.web.category;

import cn.sharenotes.core.utils.ResponseUtil;
import cn.sharenotes.db.model.dto.CategoryDTO;
import cn.sharenotes.db.service.CategoriesService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 76905
 */
@RestController
@RequestMapping("/wx/category")
public class WxCategoryController {

    @Autowired
    CategoriesService categoriesService;

    @ApiOperation(value = "通过 meanId 获取目录")
    @PostMapping("/getAll/{userId}/{menuId}")
    public Object getAllCategories(@PathVariable("userId") Integer userId,@PathVariable("menuId") Integer menuId){
        List<CategoryDTO> categoryDTOS = categoriesService.findCategoriesByUserOpenId(userId,menuId);
        if(CollectionUtils.isEmpty(categoryDTOS)){
            return ResponseUtil.fail(601,"没有目录");
        }
        return ResponseUtil.ok(categoryDTOS);
    }
}
