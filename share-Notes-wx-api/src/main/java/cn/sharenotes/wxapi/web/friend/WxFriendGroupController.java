package cn.sharenotes.wxapi.web.friend;

import cn.sharenotes.core.utils.ResponseUtil;
import cn.sharenotes.db.model.dto.GroupDto;
import cn.sharenotes.db.model.dto.GroupDtoKey;
import cn.sharenotes.db.model.dto.GroupEndDto;
import cn.sharenotes.core.service.CategoriesService;
import cn.sharenotes.db.utils.ForMateFriendUtil;
import cn.sharenotes.wxapi.service.UserGroupsSerive;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author hu
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
    public Object getAllCategories(/*@LoginUser Integer userId,*/ ) throws JsonProcessingException {
        Map<GroupDtoKey, List<GroupDto>> groupDtoMap= userGroupsMapper.selectFrindByUseId(4);
        if(CollectionUtils.isEmpty(groupDtoMap)){
            return ResponseUtil.fail(701,"没有朋友");
        }


        String res = ForMateFriendUtil.friendList(groupDtoMap);
        List<GroupEndDto> listMain = new  ArrayList<> ();
        for (GroupDtoKey groupDtoKey:
                groupDtoMap.keySet()) {
            GroupEndDto groupEndDto = new GroupEndDto(groupDtoKey.getId(),groupDtoKey.getRegion(),groupDtoMap.get(groupDtoKey));
            listMain.add(groupEndDto);
        }

        Collections.sort(listMain, new Comparator<GroupEndDto>() {
            @Override
            public int compare(GroupEndDto o1, GroupEndDto o2) {
                return o1.getId()-o2.getId();
            }
        });

        return ResponseUtil.ok(listMain);
    }
}
