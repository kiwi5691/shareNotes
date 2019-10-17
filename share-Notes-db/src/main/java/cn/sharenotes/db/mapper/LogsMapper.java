package cn.sharenotes.db.mapper;

import cn.sharenotes.db.domain.Logs;
import cn.sharenotes.db.domain.LogsExample;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface LogsMapper {
    long countByExample(LogsExample example);

    int deleteByExample(LogsExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Logs record);

    int insertSelective(Logs record);

    List<Logs> selectByExample(LogsExample example);

    Logs selectByPrimaryKey(Long id);

    int deleteAll(Date date);

    int updateByExampleSelective(@Param("record") Logs record, @Param("example") LogsExample example);

    int updateByExample(@Param("record") Logs record, @Param("example") LogsExample example);

    int updateByPrimaryKeySelective(Logs record);

    int updateByPrimaryKey(Logs record);
}