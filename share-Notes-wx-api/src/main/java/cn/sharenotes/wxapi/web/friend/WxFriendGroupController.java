package cn.sharenotes.wxapi.web.friend;

import cn.sharenotes.core.utils.JSONChange;
import cn.sharenotes.core.utils.ResponseUtil;
import cn.sharenotes.db.domain.User;
import cn.sharenotes.db.model.dto.CategoryDTO;
import cn.sharenotes.db.model.dto.GroupDto;
import cn.sharenotes.db.model.dto.GroupDtoKey;
import cn.sharenotes.db.service.CategoriesService;
import cn.sharenotes.db.utils.ForMateFriendUtil;
import cn.sharenotes.wxapi.annotation.LoginUser;
import cn.sharenotes.wxapi.service.UserGroupsSerive;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
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
    @GetMapping("/getAll")
    public Object getAllCategories() throws JsonProcessingException {
//        if (userId == null) {
//            return ResponseUtil.unlogin();
//        }
        Map<GroupDtoKey, List<GroupDto>> groupDtoMap= userGroupsMapper.selectFrindByUseId(3);
        if(CollectionUtils.isEmpty(groupDtoMap)){
            return ResponseUtil.fail(701,"没有朋友");
        }

       // String jsonStr = JSONChange.objToJson(groupDtoMap);
//        List<User> userList = new ArrayList<>();
//        User user = new User();
//        user.setId(1);
//        user.setNickname("sadsa");
//        userList.add(user);
//        userList.add(user);
//        userList.add(user);
//        userList.add(user);

        String res = ForMateFriendUtil.friendList(groupDtoMap);
        List<String> listMain = new  ArrayList<String> ();

        listMain.add(res);

//        String str=JSONChange.objToJson(listMain).toString();
        Map<String, Object> result = new HashMap<>();

//       String jsonStr1 = JSONChange.objToJson(listMain);
//        result.put("listMain", jsonStr1);
        return ResponseUtil.ok(result);
    }
}
