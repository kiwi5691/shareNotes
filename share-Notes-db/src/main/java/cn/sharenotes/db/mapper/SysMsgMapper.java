package cn.sharenotes.db.mapper;

import cn.sharenotes.db.domain.SysMsg;
import cn.sharenotes.db.domain.SysMsgExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysMsgMapper {
    long countByExample(SysMsgExample example);

    int deleteByExample(SysMsgExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysMsg record);

    int insertSelective(SysMsg record);

    List<SysMsg> selectByExampleWithBLOBs(SysMsgExample example);

    List<SysMsg> selectByExample(SysMsgExample example);

    SysMsg selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysMsg record, @Param("example") SysMsgExample example);

    int updateByExampleWithBLOBs(@Param("record") SysMsg record, @Param("example") SysMsgExample example);

    int updateByExample(@Param("record") SysMsg record, @Param("example") SysMsgExample example);

    int updateByPrimaryKeySelective(SysMsg record);

    int updateByPrimaryKeyWithBLOBs(SysMsg record);

    int updateByPrimaryKey(SysMsg record);
}