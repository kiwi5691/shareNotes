package cn.sharenotes.wxapi.service.esService;

import cn.sharenotes.db.domain.Posts;
import org.springframework.lang.Nullable;

import java.util.List;

public interface PostService {

    List<Posts> findAll(@Nullable String var1);

}
