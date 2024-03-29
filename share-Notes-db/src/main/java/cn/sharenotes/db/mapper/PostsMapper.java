package cn.sharenotes.db.mapper;

import cn.sharenotes.db.domain.Posts;
import cn.sharenotes.db.domain.PostsExample;
import cn.sharenotes.db.domain.PostsWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PostsMapper {
    long countByExample(PostsExample example);

    int deleteByExample(PostsExample example);

    List<PostsWithBLOBs> listPostsWithBLOBsByIds(List<Integer> ids);

    int deleteByPrimaryKey(Integer id);

    int insert(PostsWithBLOBs record);

    int insertSelective(PostsWithBLOBs record);

    Integer listAllPostsName(Integer id);

    List<PostsWithBLOBs> selectByExampleWithBLOBs(PostsExample example);

    List<Posts> selectByExample(PostsExample example);

    PostsWithBLOBs selectByPrimaryKey(Integer id);

    List<PostsWithBLOBs> listAllPosts();

    int updateByExampleSelective(@Param("record") PostsWithBLOBs record, @Param("example") PostsExample example);

    int updateByExampleWithBLOBs(@Param("record") PostsWithBLOBs record, @Param("example") PostsExample example);

    int updateByExample(@Param("record") Posts record, @Param("example") PostsExample example);

    int updateByPrimaryKeySelective(PostsWithBLOBs record);

    int incrVisit(Integer id);

    int updateByPrimaryKeyWithBLOBs(PostsWithBLOBs record);

    int updateByPrimaryKey(Posts record);
}