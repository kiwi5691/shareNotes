package cn.sharenotes.core.aop.cache;

import cn.sharenotes.db.domain.PostsWithBLOBs;
import cn.sharenotes.db.domain.index.PostsIndex;
import cn.sharenotes.db.utils.DtoUtils;
import javafx.geometry.Pos;

import java.util.List;
import java.util.Optional;

public class IndexThreadLocal {
    public static ThreadLocal<PostsIndex> postsIndexThreadLocal = new ThreadLocal<>();
    public static ThreadLocal<List<PostsIndex>> postsIndexListThreadLocal = new ThreadLocal<>();
    public static PostsIndex get(){
        Optional<PostsIndex> postsIndex = Optional.ofNullable(postsIndexThreadLocal.get());
        return postsIndex.orElse(null);
    }
    public static void set(PostsIndex postsIndex){
        postsIndexThreadLocal.set(postsIndex);
    }
    public static void remove(){
         postsIndexThreadLocal.remove();
    }
    public static void setList(List<PostsWithBLOBs> postsIndex){
        List<PostsIndex> postsIndices = DtoUtils.convertList2List(postsIndex,PostsIndex.class);
        postsIndexListThreadLocal.set(postsIndices);
    }
    public static List<PostsIndex> getList(){
        Optional<List<PostsIndex>> postsIndices = Optional.ofNullable(postsIndexListThreadLocal.get());
        return postsIndices.orElse(null);

    }
    public static void removeList(){
        postsIndexListThreadLocal.remove();
    }

}
