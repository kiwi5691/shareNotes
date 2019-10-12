package cn.sharenotes.core.service.Impl;

import cn.sharenotes.core.redis.RedisManager;
import cn.sharenotes.core.service.UserGroupsSerive;
import cn.sharenotes.core.utils.NameIdUtils;
import cn.sharenotes.db.domain.UserGroups;
import cn.sharenotes.db.mapper.UserGroupsMapper;
import cn.sharenotes.db.mapper.UserMapper;
import cn.sharenotes.db.model.dto.GroupDto;
import cn.sharenotes.db.model.dto.GroupDtoKey;
import cn.sharenotes.db.utils.DtoUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UserGroupsSeriveImp implements UserGroupsSerive {

    @Autowired
    UserGroupsMapper userGroupsMapper;

    @Value("${FRIEND_BY_ID}")
    private String FRIEND_BY_ID;

    @Autowired
    UserMapper userMapper;

    @Resource
    private RedisManager redisManager;

    @Override
    public Map<GroupDtoKey, List<GroupDto>> selectFrindByUseId(Integer id) {
        List<GroupDto> friendGroup = new ArrayList<>();

        for (Integer friendId : userGroupsMapper.selectFrindByUseId(id)) {
            GroupDto groupDto = new GroupDto();
            DtoUtils.copyProperties(userMapper.selectByPrimaryKey(friendId), groupDto);
            friendGroup.add(groupDto);

        }

        Map<GroupDtoKey, List<GroupDto>> groupDtoMap = NameIdUtils.sort(friendGroup);
        return groupDtoMap;
    }

    @Override
    public boolean addFriend(Integer userId, Integer friendId) {
        UserGroups userGroups = new UserGroups();
        UserGroups groups = new UserGroups();
        userGroups.setUserId(userId);
        userGroups.setFriendId(friendId);
        groups.setUserId(friendId);
        groups.setFriendId(userId);
        if(userGroupsMapper.insert(userGroups)>0 && userGroupsMapper.insert(groups)>0){
            return true;
        }
        return false;
    }

}
