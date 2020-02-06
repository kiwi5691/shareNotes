package cn.sharenotes.wxapi.service.esService;

import cn.sharenotes.db.domain.Posts;
import cn.sharenotes.db.domain.PostsWithBLOBs;
import org.springframework.lang.Nullable;

import java.util.List;

public interface PostService {

    Iterable<PostsWithBLOBs> findAll(@Nullable String var1);

}
