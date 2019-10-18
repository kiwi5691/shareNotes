package cn.sharenotes.wxapi.web.friend;

import cn.sharenotes.core.enums.ContentBase;
import cn.sharenotes.core.service.CategoriesService;
import cn.sharenotes.core.service.UserGroupsSerive;
import cn.sharenotes.core.utils.ResponseUtil;
import cn.sharenotes.db.model.dto.CategoryDTO;

import cn.sharenotes.wxapi.annotation.LoginUser;
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
 * @author hu
 */
@RestController
@RequestMapping("/wx/friend")
public class WxFriendTypeController {

    @Autowired
    CategoriesService categoriesService;
    @Autowired
    private UserGroupsSerive userGroupsMapper;
    @ApiOperation(value = "通过 UserId 获取目录")
    @GetMapping("/getAll/menu/{fid}")
    public Object getAllCategories(@LoginUser Integer userId, @PathVariable("fid") Integer fid){
        List<CategoryDTO> categoryDTOS = categoriesService.friendFindCategoriesByUserOpenIdWithMenuId(fid, ContentBase.PUBLICID.getValue());
        if(CollectionUtils.isEmpty(categoryDTOS)){
            return ResponseUtil.fail(721,"您的朋友还没有目录");
        }
        Map<String, Object> result = new HashMap<>();

        result.put("publicCate", categoryDTOS);

        return ResponseUtil.ok(result);
    }
}
