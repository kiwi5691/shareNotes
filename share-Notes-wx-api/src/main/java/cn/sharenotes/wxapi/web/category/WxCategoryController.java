package cn.sharenotes.wxapi.web.category;

import cn.sharenotes.core.utils.ResponseUtil;
import cn.sharenotes.db.model.dto.CategoryDTO;
import cn.sharenotes.db.service.CategoriesService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        List<CategoryDTO> categoryDTOS = categoriesService.findCategoriesByUserOpenId(5, menuId);
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

    @ApiOperation(value = "创建目录")
    @GetMapping("/add")
    public Object addCategory(/*@LoginUser Integer userId,*/ ) {
        // TODO: 2019/10/4 参数
//   TODO     iconSelected: "活动"    ----------还有 手记  ，内容        ！！！分别对应cn.sharenotes.core.enums.IconType的1,3,2
//            isPcOrPr: false       ！！ false是私人，true是公共
//            name: "11"    目录名字
        return "fuck";
    }
}