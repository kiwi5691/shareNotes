package cn.sharenotes.core.service;

import cn.sharenotes.db.model.dto.GroupDto;
import cn.sharenotes.db.model.dto.GroupDtoKey;

import java.util.List;
import java.util.Map;

public interface UserGroupsSerive {
    /**
     * 通过用户id获取朋友id集合
     *
     * @return 朋友id集合<XxxxDO>
     */
    Map<GroupDtoKey, List<GroupDto>> selectFrindByUseId(Integer id);

    boolean addFriend(Integer userId,Integer friendId);

    /**
     * 通过用户id删除朋友
     *
     * @return 朋友id集合<XxxxDO>
     */
    Integer deleteFriend(Integer userId,Integer friendId);

}
