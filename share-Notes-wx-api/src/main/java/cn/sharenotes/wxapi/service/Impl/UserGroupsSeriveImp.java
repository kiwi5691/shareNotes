package cn.sharenotes.wxapi.service.Impl;

import cn.sharenotes.core.utils.NameIdUtils;
import cn.sharenotes.db.dao.UserGroupsMapper;
import cn.sharenotes.db.dao.UserMapper;
import cn.sharenotes.db.model.dto.GroupDto;
import cn.sharenotes.db.model.dto.GroupDtoKey;
import cn.sharenotes.db.utils.DtoUtils;
import cn.sharenotes.wxapi.service.UserGroupsSerive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserGroupsSeriveImp implements UserGroupsSerive {
    @Autowired
    UserGroupsMapper userGroupsMapper;
    @Autowired
    UserMapper userMapper;
    @Override
    public Map<GroupDtoKey, List<GroupDto>> selectFrindByUseId(Integer id) {
        List<GroupDto> friendGroup = new ArrayList<>();
        for (Integer friendId: userGroupsMapper.selectFrindByUseId(id)) {
            GroupDto groupDto = new GroupDto();

          DtoUtils.copyProperties(userMapper.selectByPrimaryKey(friendId),groupDto);
            friendGroup.add(groupDto);
        }

        Map<GroupDtoKey,List<GroupDto>> groupDtoMap = NameIdUtils.sort(friendGroup);
        return groupDtoMap;
    }
}
