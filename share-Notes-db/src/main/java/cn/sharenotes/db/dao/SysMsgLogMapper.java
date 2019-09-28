package cn.sharenotes.db.dao;

import cn.sharenotes.db.domain.SysMsgLog;
import cn.sharenotes.db.domain.SysMsgLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysMsgLogMapper {
    long countByExample(SysMsgLogExample example);

    int deleteByExample(SysMsgLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SysMsgLog record);

    int insertSelective(SysMsgLog record);

    List<SysMsgLog> selectByExample(SysMsgLogExample example);

    SysMsgLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SysMsgLog record, @Param("example") SysMsgLogExample example);

    int updateByExample(@Param("record") SysMsgLog record, @Param("example") SysMsgLogExample example);

    int updateByPrimaryKeySelective(SysMsgLog record);

    int updateByPrimaryKey(SysMsgLog record);
}