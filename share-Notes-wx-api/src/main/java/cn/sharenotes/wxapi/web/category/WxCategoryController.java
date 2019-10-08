package cn.sharenotes.wxapi.web.category;

import cn.sharenotes.core.service.CategoriesService;
import cn.sharenotes.core.utils.CategoryUtils;
import cn.sharenotes.core.utils.JacksonUtil;
import cn.sharenotes.core.utils.ResponseUtil;
import cn.sharenotes.db.model.dto.CategoryDTO;
import cn.sharenotes.db.model.dto.CategoryDetailDTO;
import cn.sharenotes.db.model.vo.CategoryVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.*;

/**
 * @author 76905
 */
@Slf4j
@RestController
@RequestMapping("/wx/category")
public class WxCategoryController {

    @Autowired
    CategoriesService categoriesService;

    @ApiOperation(value = "通过 meanId 获取目录")
    @GetMapping("/getAll/{menuId}")
    public Object getAllCategories(/*@LoginUser Integer userId,*/ @PathVariable("menuId") Integer menuId) {
        Integer userId= 5;
        //到时候删除
        List<CategoryDTO> categoryDTOS = categoriesService.findCategoriesByUserOpenIdWithMenuId(userId, menuId);
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
        Integer userId= 5;
        //到时候删除
        CategoryVO categoryVO = getBodyIntoCategoryVO(userId,body);
        if(categoryVO == null){
            return ResponseUtil.fail(602, "添加目录失败，目录名存在");
        }
        if (categoriesService.addCategory(userId, categoryVO) > 0) {
            categoriesService.updateCategoriesRedisInfo(userId,CategoryUtils.chekcIsPcOrPr(JacksonUtil.parseBoolean(body, "isPcOrPr")));
            return ResponseUtil.ok();
        }
        return ResponseUtil.fail();
    }

    @ApiOperation(value = "通过 categoryId 删除目录")
    @DeleteMapping("/delete")
    public Object deleteCategory(/*@LoginUser Integer userId,*/@RequestBody String body ) {
        Integer userId= 5;
        int categoryId = JacksonUtil.parseInteger(body, "cateId");
       if(categoriesService.deleteCategoryByCategoryId(categoryId)>0){
           categoriesService.updateCategoriesRedisInfo(userId,CategoryUtils.checkMenu_id(Objects.requireNonNull(JacksonUtil.parseString(body, "menu_id"))));
           return ResponseUtil.ok();
       }
        return ResponseUtil.fail();
    }

    @ApiOperation(value = "通过 categoryId 修改目录")
    @PutMapping("update")
    public Object updateCategoryByCategoryId(/*@LoginUser Integer userId,*/ @RequestBody String body){
        log.info("id"+JacksonUtil.parseInteger(body, "cateId"));

        Integer userId= 5;
        //到时候删除

        CategoryVO categoryVO = getBodyIntoCategoryVO(userId,body);
        if(categoryVO == null){
            return ResponseUtil.fail(603, "修改目录失败,目录名已存在");
        }
        log.info("id"+JacksonUtil.parseInteger(body, "cateId"));
        if(categoriesService.updateCategoryByCategoryId(JacksonUtil.parseInteger(body, "cateId"), categoryVO) > 0){
            categoriesService.updateCategoriesRedisInfo(userId,CategoryUtils.chekcIsPcOrPr(JacksonUtil.parseBoolean(body, "isPcOrPr")));
            return ResponseUtil.ok();
        }
        return ResponseUtil.fail();
    }

    @ApiOperation(value = "通过 categoryId 获取详细目录")
    @GetMapping("detail/{categoryId}")
    public Object getCategoryDetail(/*@LoginUser Integer userId,*/ @PathVariable("categoryId") Integer categoryId){
        Integer userId= 5;
        //到时候删除
        Optional<CategoryDetailDTO> categoryDTO =null;

        categoryDTO= Optional.ofNullable(categoriesService.findCategoriesDetailByCid(userId, categoryId));
        Map<String, Object> result = new HashMap<>();

        result.put("cateName", categoryDTO.get().getName());
        result.put("current", CategoryUtils.getDescription(categoryDTO.get().getDescription()));
        result.put("switch1", CategoryUtils.getMenuBoolean(categoryDTO.get().getMenuId()));

        return ResponseUtil.ok(result);
    }

    private CategoryVO getBodyIntoCategoryVO(Integer userId, String body){
        String name = JacksonUtil.parseString(body, "name");
        boolean isPcOrPr = JacksonUtil.parseBoolean(body, "isPcOrPr");
        String iconSelected = JacksonUtil.parseString(body, "iconSelected");
        log.info("id"+JacksonUtil.parseString(body, "name"));
        log.info("id"+JacksonUtil.parseBoolean(body, "isPcOrPr"));
        log.info("id"+JacksonUtil.parseString(body, "iconSelected"));

        List<String> nameList = categoriesService.findAllCategoryNameByUserOpenIdWithMenuId(userId, CategoryUtils.chekcIsPcOrPr(isPcOrPr));
        if (nameList.contains(name)) {
            return null;
        }
        CategoryVO categoryVO = new CategoryVO();
        categoryVO.setName(name);
        categoryVO.setParentId(CategoryUtils.chekcIsPcOrPr(isPcOrPr));
        assert iconSelected != null;
        categoryVO.setDescription(CategoryUtils.chekciconSelected(iconSelected));
        return categoryVO;
    }

}