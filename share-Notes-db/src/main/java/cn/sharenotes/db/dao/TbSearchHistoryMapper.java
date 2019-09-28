package cn.sharenotes.db.dao;

import cn.sharenotes.db.domain.TbSearchHistory;
import cn.sharenotes.db.domain.TbSearchHistoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbSearchHistoryMapper {
    long countByExample(TbSearchHistoryExample example);

    int deleteByExample(TbSearchHistoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbSearchHistory record);

    int insertSelective(TbSearchHistory record);

    List<TbSearchHistory> selectByExample(TbSearchHistoryExample example);

    TbSearchHistory selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbSearchHistory record, @Param("example") TbSearchHistoryExample example);

    int updateByExample(@Param("record") TbSearchHistory record, @Param("example") TbSearchHistoryExample example);

    int updateByPrimaryKeySelective(TbSearchHistory record);

    int updateByPrimaryKey(TbSearchHistory record);
}