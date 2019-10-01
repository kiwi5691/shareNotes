package cn.sharenotes.wxapi.web.category;

import cn.sharenotes.core.utils.ResponseUtil;
import cn.sharenotes.db.model.dto.CategoryDTO;
import cn.sharenotes.db.service.CategoriesService;
import cn.sharenotes.wxapi.annotation.LoginUser;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 76905
 */
@RestController
@RequestMapping("/wx/category")
public class WxCategoryController {

    @Autowired
    CategoriesService categoriesService;

    @ApiOperation(value = "通过 meanId 获取目录")
    @GetMapping("/getAll/{menuId}")
    //请你用@LoginUSER Integer userId！
    public Object getAllCategories(/*@LoginUser Integer userId,*/ @PathVariable("menuId") Integer menuId){
        List<CategoryDTO> categoryDTOS = categoriesService.findCategoriesByUserOpenId(5,menuId);
        if(CollectionUtils.isEmpty(categoryDTOS)){
            return ResponseUtil.fail(601,"没有目录");
        }
        Map<String, Object> result = new HashMap<>();
        if(menuId==1){
            result.put("publicCate", categoryDTOS);
        }
        if(menuId==2){
            result.put("privateCate", categoryDTOS);
        }
        return ResponseUtil.ok(result);
    }
}
