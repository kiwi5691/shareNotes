package cn.sharenotes.wxapi.web.category;

import cn.sharenotes.core.service.CategoriesService;
import cn.sharenotes.core.utils.ResponseUtil;
import cn.sharenotes.db.model.dto.CategoryDTO;
import cn.sharenotes.db.model.vo.CategoryVO;
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

    @ApiOperation(value = "添加目录")
    @PostMapping("/add")
    public Object addCategories(/*@LoginUser Integer userId,*/String name,boolean isPcOrPr,String iconSelected){
        List<String> allCategoriesName = categoriesService.findAllCategoriesName();
        if(!allCategoriesName.contains(name)){
            return ResponseUtil.fail(602,"添加目录失败，目录名存在");
        }
        CategoryVO categoryVO = new CategoryVO();
        categoryVO.setName(name);
        if(isPcOrPr == true){
            categoryVO.setParentId(1);
        }else {
            categoryVO.setParentId(2);
        }
        String description = null;
        if(iconSelected.equals("活动")){
            description = "activity";
        }else if(iconSelected.equals("手记")){
            description = "barrage";
        }else {
            description = "brush";
        }
        categoryVO.setDescription(description);
        int i = categoriesService.addCategories(5,categoryVO);
        if(i<0){
            return ResponseUtil.fail(603,"添加目录失败");
        }
        return ResponseUtil.ok();
    }
}
