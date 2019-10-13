package cn.sharenotes.db.mapper;

import cn.sharenotes.db.domain.Attachments;
import cn.sharenotes.db.domain.AttachmentsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AttachmentsMapper {
    long countByExample(AttachmentsExample example);

    int deleteByExample(AttachmentsExample example);

    Attachments selectOneByKey(String key);

    int deleteByPrimaryKey(Integer id);

    int insert(Attachments record);

    int insertSelective(Attachments record);

    List<Attachments> selectByExample(AttachmentsExample example);

    Attachments selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Attachments record, @Param("example") AttachmentsExample example);

    int updateByExample(@Param("record") Attachments record, @Param("example") AttachmentsExample example);

    int updateByPrimaryKeySelective(Attachments record);

    int logicalDeleteByExample(@Param("example") AttachmentsExample example);

    int updateByPrimaryKey(Attachments record);
}