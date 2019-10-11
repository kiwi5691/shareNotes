package cn.sharenotes.db.mapper;

import cn.sharenotes.db.domain.PostCategories;
import cn.sharenotes.db.domain.PostCategoriesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PostCategoriesMapper {
    long countByExample(PostCategoriesExample example);

    int deleteByExample(PostCategoriesExample example);

    int deleteByPrimaryKey(Integer id);

    int deleteByCategories(Integer cateid);

    int insert(PostCategories record);

    int insertSelective(PostCategories record);

    List<PostCategories> selectByExample(PostCategoriesExample example);

    List<Integer> selectPostidByCateid(Integer cateid);

    PostCategories selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PostCategories record, @Param("example") PostCategoriesExample example);

    int updateByExample(@Param("record") PostCategories record, @Param("example") PostCategoriesExample example);

    int updateByPrimaryKeySelective(PostCategories record);

    int updateByPrimaryKey(PostCategories record);
}