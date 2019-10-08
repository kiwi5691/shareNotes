package cn.sharenotes.core.service.Impl;

import cn.sharenotes.core.redis.KeyPrefix.OwnerContentKey;
import cn.sharenotes.core.redis.RedisManager;
import cn.sharenotes.core.utils.NameIdUtils;
import cn.sharenotes.db.mapper.UserGroupsMapper;
import cn.sharenotes.db.mapper.UserMapper;
import cn.sharenotes.db.model.dto.GroupDto;
import cn.sharenotes.db.model.dto.GroupDtoKey;

import cn.sharenotes.db.model.dto.PostDTO;
import cn.sharenotes.db.utils.DtoUtils;
import cn.sharenotes.core.service.UserGroupsSerive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
       List<GroupDto> finalFriendGroup = null;

//        Optional.ofNullable(friendGroup).orElseGet(()->null);
//       finalFriendGroup = (List<GroupDto>) redisManager.getList(FRIEND_BY_ID + ":" + "userid :" + id);

//        if (CollectionUtils.isEmpty(finalFriendGroup)) {
            for (Integer friendId : userGroupsMapper.selectFrindByUseId(id)) {
                GroupDto groupDto = new GroupDto();
                DtoUtils.copyProperties(userMapper.selectByPrimaryKey(friendId), groupDto);
                friendGroup.add(groupDto);

        }
            finalFriendGroup = friendGroup;
//            redisManager.setList(FRIEND_BY_ID+":"+"userid :"+id, finalFriendGroup);


        Map<GroupDtoKey, List<GroupDto>> groupDtoMap = NameIdUtils.sort(friendGroup);
        return groupDtoMap;
  }
}
