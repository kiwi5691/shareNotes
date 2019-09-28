package cn.sharenotes.db.dao;

import cn.sharenotes.db.domain.Posts;
import cn.sharenotes.db.domain.PostsExample;
import cn.sharenotes.db.domain.PostsWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PostsMapper {
    long countByExample(PostsExample example);

    int deleteByExample(PostsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PostsWithBLOBs record);

    int insertSelective(PostsWithBLOBs record);

    List<PostsWithBLOBs> selectByExampleWithBLOBs(PostsExample example);

    List<Posts> selectByExample(PostsExample example);

    PostsWithBLOBs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PostsWithBLOBs record, @Param("example") PostsExample example);

    int updateByExampleWithBLOBs(@Param("record") PostsWithBLOBs record, @Param("example") PostsExample example);

    int updateByExample(@Param("record") Posts record, @Param("example") PostsExample example);

    int updateByPrimaryKeySelective(PostsWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(PostsWithBLOBs record);

    int updateByPrimaryKey(Posts record);
}