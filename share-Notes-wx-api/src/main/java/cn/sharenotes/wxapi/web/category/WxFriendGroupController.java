package cn.sharenotes.wxapi.web.category;

import cn.sharenotes.core.utils.ResponseUtil;
import cn.sharenotes.db.model.dto.CategoryDTO;
import cn.sharenotes.db.model.dto.GroupDto;
import cn.sharenotes.db.model.dto.GroupDtoKey;
import cn.sharenotes.db.service.CategoriesService;
import cn.sharenotes.wxapi.service.UserGroupsSerive;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author 76905
 */
@RestController
@RequestMapping("/wx/friend")
public class WxFriendGroupController {

    @Autowired
    CategoriesService categoriesService;
    @Autowired
    private UserGroupsSerive userGroupsMapper;
    @ApiOperation(value = "通过 UserId 获取目录")
    @PostMapping("/getAll/{userId}")
    public Object getAllCategories(@PathVariable("userId") Integer userId){
        Map<GroupDtoKey, List<GroupDto>> groupDtoMap= userGroupsMapper.selectFrindByUseId(userId);
        if(CollectionUtils.isEmpty(groupDtoMap)){
            return ResponseUtil.fail(701,"没有朋友");
        }
        return ResponseUtil.ok(groupDtoMap);
    }
}
