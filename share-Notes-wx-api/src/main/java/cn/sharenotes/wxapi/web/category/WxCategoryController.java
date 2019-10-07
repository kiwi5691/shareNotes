package cn.sharenotes.wxapi.web.category;

import cn.sharenotes.core.service.CategoriesService;
import cn.sharenotes.core.utils.JacksonUtil;
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
    public Object getAllCategories(/*@LoginUser Integer userId,*/ @PathVariable("menuId") Integer menuId) {
        List<CategoryDTO> categoryDTOS = categoriesService.findCategoriesByUserOpenIdWithMenuId(5, menuId);
        if (CollectionUtils.isEmpty(categoryDTOS)) {
            return ResponseUtil.fail(601, "没有目录");
        }
        Map<String, Object> result = new HashMap<>();
        if (menuId == 1) {
            result.put("publicCate", categoryDTOS);
        }
        if (menuId == 2) {
            result.put("privateCate", categoryDTOS);
        }
        return ResponseUtil.ok(result);
    }

    @ApiOperation(value = "添加目录")
    @PostMapping("/add")
    public Object addCategory(/*@LoginUser Integer userId,*/@RequestBody String body) {
        CategoryVO categoryVO = getBodyIntoCategoryVO(body);
        if(categoryVO == null){
            return ResponseUtil.fail(602, "添加目录失败，目录名存在");
        }
        if (categoriesService.addCategory(5, categoryVO) > 0) {
            return ResponseUtil.ok();
        }
        return ResponseUtil.fail();
    }

    @ApiOperation(value = "通过 categoryId 删除目录")
    @DeleteMapping("/delete/{categoryId}")
    public Object deleteCategory(/*@LoginUser Integer userId,*/ @PathVariable("categoryId") Integer categoryId) {
       categoriesService.deleteCategoryByCategoryId(categoryId);
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "通过 categoryId 修改目录")
    @PutMapping("update/{categoryId}")
    public Object updateCategoryByCategoryId(@PathVariable("categoryId") Integer categoryId,@RequestBody String body){
        CategoryVO categoryVO = getBodyIntoCategoryVO(body);
        if(categoryVO == null){
            return ResponseUtil.fail(603, "修改目录失败,目录名已存在");
        }
        if(categoriesService.updateCategoryByCategoryId(categoryId, categoryVO) > 0){
            return ResponseUtil.ok();
        }
        return ResponseUtil.updatedDataFailed();
    }

    public CategoryVO getBodyIntoCategoryVO(String body){
        String name = JacksonUtil.parseString(body, "name");
        boolean isPcOrPr = JacksonUtil.parseBoolean(body, "isPcOrPr");
        String iconSelected = JacksonUtil.parseString(body, "iconSelected");
        String description = null;
        Integer menuId = null;
        if (isPcOrPr) {
            menuId = 1;
        } else {
            menuId = 2;
        }
        List<String> nameList = categoriesService.findAllCategoryNameByUserOpenIdWithMenuId(5, menuId);
        if (nameList.contains(name)) {
            return null;
        }
        CategoryVO categoryVO = new CategoryVO();
        categoryVO.setName(name);
        categoryVO.setParentId(menuId);
        if (iconSelected.equals("活动")) {
            description = "activity";
        } else if (iconSelected.equals("手记")) {
            description = "barrage";
        } else {
            description = "brush";
        }
        categoryVO.setDescription(description);
        return categoryVO;
    }

}