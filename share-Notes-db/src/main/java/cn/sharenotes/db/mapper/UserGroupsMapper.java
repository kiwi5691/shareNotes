package cn.sharenotes.db.mapper;

import cn.sharenotes.db.domain.UserGroups;
import cn.sharenotes.db.domain.UserGroupsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserGroupsMapper {
    long countByExample(UserGroupsExample example);

    int deleteByExample(UserGroupsExample example);

    int deleteByPrimaryKey(Integer id);

    int deleteFriend(Integer userId,Integer friendId);

    int insert(UserGroups record);

    int insertSelective(UserGroups record);

    /**
     * 通过用户id获取朋友id集合
     *
     * @return 朋友id集合<XxxxDO>
     */
    List<Integer> selectFrindByUseId(Integer id);

    List<UserGroups> selectByExample(UserGroupsExample example);

    UserGroups selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserGroups record, @Param("example") UserGroupsExample example);

    int updateByExample(@Param("record") UserGroups record, @Param("example") UserGroupsExample example);

    int updateByPrimaryKeySelective(UserGroups record);

    int updateByPrimaryKey(UserGroups record);
}