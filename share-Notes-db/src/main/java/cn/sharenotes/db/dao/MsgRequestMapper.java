package cn.sharenotes.db.dao;

import cn.sharenotes.db.domain.MsgRequest;
import cn.sharenotes.db.domain.MsgRequestExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MsgRequestMapper {
    long countByExample(MsgRequestExample example);

    int deleteByExample(MsgRequestExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MsgRequest record);

    int insertSelective(MsgRequest record);

    List<MsgRequest> selectByExample(MsgRequestExample example);

    MsgRequest selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MsgRequest record, @Param("example") MsgRequestExample example);

    int updateByExample(@Param("record") MsgRequest record, @Param("example") MsgRequestExample example);

    int updateByPrimaryKeySelective(MsgRequest record);

    int updateByPrimaryKey(MsgRequest record);
}