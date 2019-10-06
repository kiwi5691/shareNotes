package cn.sharenotes.db.mapper;

import cn.sharenotes.db.domain.Categories;
import cn.sharenotes.db.domain.CategoriesExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoriesMapper {
    long countByExample(CategoriesExample example);

    int deleteByExample(CategoriesExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Categories record);

    int insertSelective(Categories record);

    List<Categories> selectByExample(CategoriesExample example);

    Categories selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Categories record, @Param("example") CategoriesExample example);

    int updateByExample(@Param("record") Categories record, @Param("example") CategoriesExample example);

    int updateByPrimaryKeySelective(Categories record);

    int updateByPrimaryKey(Categories record);
}