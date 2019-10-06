package cn.sharenotes.wxapi.web.friend;

import cn.sharenotes.core.service.CategoriesService;
import cn.sharenotes.core.utils.ResponseUtil;
import cn.sharenotes.db.model.dto.CategoryDTO;
import cn.sharenotes.db.model.dto.GroupDto;
import cn.sharenotes.db.model.dto.GroupDtoKey;
import cn.sharenotes.db.model.dto.GroupEndDto;
import cn.sharenotes.db.utils.ForMateFriendUtil;
import cn.sharenotes.wxapi.service.UserGroupsSerive;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

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
    @GetMapping("/getAll/menu")
    public Object getAllCategories() throws JsonProcessingException {
        List<CategoryDTO> categoryDTOS = categoriesService.findCategoriesByUserOpenId(5,1);
        if(CollectionUtils.isEmpty(categoryDTOS)){
            return ResponseUtil.fail(601,"没有目录");
        }
        Map<String, Object> result = new HashMap<>();

            result.put("publicCate", categoryDTOS);

        return ResponseUtil.ok(result);
    }
}
