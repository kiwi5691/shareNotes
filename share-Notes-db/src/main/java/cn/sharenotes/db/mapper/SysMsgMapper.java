package cn.sharenotes.db.mapper;

import cn.sharenotes.db.domain.SysMsg;
import cn.sharenotes.db.domain.SysMsgExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysMsgMapper {
    long countByExample(SysMsgExample example);

    int deleteByExample(SysMsgExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SysMsg record);

    int insertSelective(SysMsg record);

    List<SysMsg> selectByExample(SysMsgExample example);

    SysMsg selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SysMsg record, @Param("example") SysMsgExample example);

    int updateByExample(@Param("record") SysMsg record, @Param("example") SysMsgExample example);

    int updateByPrimaryKeySelective(SysMsg record);

    int updateByPrimaryKey(SysMsg record);

    List<SysMsg> selectByRecentId(int recentId);
}