package cn.sharenotes.wxapi.web.category;

import cn.binarywang.wx.miniapp.api.WxMaSecCheckService;
import cn.sharenotes.core.service.CategoriesService;
import cn.sharenotes.core.service.PostContentService;
import cn.sharenotes.core.utils.CategoryUtils;
import cn.sharenotes.core.utils.JacksonUtil;
import cn.sharenotes.core.utils.ResponseUtil;
import cn.sharenotes.db.model.dto.CategoryDTO;
import cn.sharenotes.db.model.dto.CategoryDetailDTO;
import cn.sharenotes.db.model.vo.CategoryVO;
import cn.sharenotes.wxapi.annotation.Log;
import cn.sharenotes.wxapi.annotation.LoginUser;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author 76905
 */
@Slf4j
@RestController
@RequestMapping("/wx/category")
public class WxCategoryController {

    @Autowired
    private WxMaSecCheckService wxMaSecCheckService;

    @Autowired
    CategoriesService categoriesService;

    @Autowired
    PostContentService postContentService;

    @ApiOperation(value = "通过 meanId 获取目录")
    @GetMapping("/getAll/{menuId}")
    public Object getAllCategories(@LoginUser Integer userId,@PathVariable("menuId") Integer menuId) {
        List<CategoryDTO> categoryDTOS = categoriesService.findCategoriesByUserOpenIdWithMenuId(userId, menuId);
        if (CollectionUtils.isEmpty(categoryDTOS)) {
            return ResponseUtil.fail(601, "您尚未创建目录");
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

    @Log("添加目录")
    @ApiOperation(value = "添加目录")
    @PostMapping("/add")
    public Object addCategory(@LoginUser Integer userId,@RequestBody String body) {
        boolean unSafe = true;
        try{
            unSafe =wxMaSecCheckService.checkMessage(JacksonUtil.parseString(body, "name"));
        }catch (WxErrorException e) {
            return ResponseUtil.fail(500,"违法违规标题");
        }
        if(!unSafe){
            return ResponseUtil.fail(500,"违法违规标题");
        }
        CategoryVO categoryVO = getBodyIntoCategoryVO(userId, body,"add");
        if (categoryVO == null) {
            return ResponseUtil.fail(602, "添加目录失败，目录名存在");
        }
        String name = JacksonUtil.parseString(body, "name");
        try{
            unSafe =wxMaSecCheckService.checkMessage(name);
        }catch (WxErrorException e) {
            return ResponseUtil.fail(202, "添加目录失败，出现违禁词");
        }
        if(!unSafe){
            return ResponseUtil.fail(202, "添加目录失败，出现违禁词");
        }
        if (categoriesService.addCategory(userId, categoryVO) > 0) {
            categoriesService.updateCategoriesRedisInfo(userId, CategoryUtils.chekcIsPcOrPr(JacksonUtil.parseBoolean(body, "isPcOrPr")));
            return ResponseUtil.ok();
        }
        return ResponseUtil.fail();
    }

    @Log("删除目录")
    @ApiOperation(value = "通过 categoryId 删除目录")
    @DeleteMapping("/delete")
    public Object deleteCategory(@LoginUser Integer userId,@RequestBody String body) {
//        Integer userId = 5;
        Integer categoryId = JacksonUtil.parseInteger(body, "cateId");
        String menuId = JacksonUtil.parseString(body, "menu_id");
        if (categoriesService.deleteCategoryByCategoryId(CategoryUtils.checkMenu_id(menuId), categoryId) > 0) {
            categoriesService.updateCategoriesRedisInfo(userId, CategoryUtils.checkMenu_id(menuId));
            postContentService.updatePostsRedisInfo(categoryId);
            return ResponseUtil.ok();
        }
        return ResponseUtil.fail();
    }

    @ApiOperation(value = "通过 categoryId 修改目录")
    @PutMapping("update")
    public Object updateCategoryByCategoryId(@LoginUser Integer userId, @RequestBody String body) {
        Integer categoryId = JacksonUtil.parseInteger(body, "cateId");

        CategoryVO categoryVO = getBodyIntoCategoryVO(userId, body,"update");
        if (categoryVO == null) {
            return ResponseUtil.fail(603, "修改目录失败,目录名已存在");

        }
        String name = JacksonUtil.parseString(body, "name");
        boolean unSafe = true;
        try{
            unSafe =wxMaSecCheckService.checkMessage(name);
        }catch (WxErrorException e) {
            return ResponseUtil.fail(202, "添加目录失败，出现违禁词");
        }
        if(!unSafe){
            return ResponseUtil.fail(202, "添加目录失败，出现违禁词");
        }

        if (categoriesService.updateCategoryByCategoryId(categoryId, categoryVO) > 0) {
            categoriesService.updateCategoriesRedisInfo(userId, CategoryUtils.chekcIsPcOrPr(JacksonUtil.parseBoolean(body, "isPcOrPr")));
            return ResponseUtil.ok();
        }
        return ResponseUtil.fail();
    }

    @ApiOperation(value = "通过 categoryId 获取详细目录")
    @GetMapping("detail/{categoryId}")
    public Object getCategoryDetail(@LoginUser Integer userId, @PathVariable("categoryId") Integer categoryId) {

        Optional<CategoryDetailDTO> categoryDTO = null;

        categoryDTO = Optional.ofNullable(categoriesService.findCategoriesDetailByCid(userId, categoryId));
        Map<String, Object> result = new HashMap<>();

        result.put("cateName", categoryDTO.get().getName());
        result.put("current", CategoryUtils.getDescription(categoryDTO.get().getDescription()));
        result.put("switch1", CategoryUtils.getMenuBoolean(categoryDTO.get().getMenuId()));

        return ResponseUtil.ok(result);
    }

    private CategoryVO getBodyIntoCategoryVO(Integer userId, String body,String methodName) {
        String name = JacksonUtil.parseString(body, "name");
        boolean isPcOrPr = JacksonUtil.parseBoolean(body, "isPcOrPr");
        String iconSelected = JacksonUtil.parseString(body, "iconSelected");
        Integer categoryId = JacksonUtil.parseInteger(body, "cateId");

        List<String> nameList = categoriesService.findAllCategoryNameByUserOpenIdWithMenuId(userId, CategoryUtils.chekcIsPcOrPr(isPcOrPr));
        CategoryDetailDTO detail = new CategoryDetailDTO();
        if(methodName.equals("update")) {
             detail = categoriesService.findCategoriesDetailByCid(userId, categoryId);
        }

        if (!CollectionUtils.isEmpty(nameList)) {
            if(methodName.equals("update")){
                nameList.remove(detail.getName());
            }
            if (nameList.contains(name)) {
                return null;
            }
        }

        CategoryVO categoryVO = new CategoryVO();
        categoryVO.setName(name);
        categoryVO.setParentId(CategoryUtils.chekcIsPcOrPr(isPcOrPr));
        assert iconSelected != null;
        categoryVO.setDescription(CategoryUtils.chekciconSelected(iconSelected));
        return categoryVO;
    }

}