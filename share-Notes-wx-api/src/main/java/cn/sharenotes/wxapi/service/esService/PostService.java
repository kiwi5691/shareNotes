package cn.sharenotes.wxapi.service.esService;

import cn.sharenotes.db.domain.Posts;
import cn.sharenotes.db.domain.PostsWithBLOBs;
import cn.sharenotes.db.domain.index.PostsIndex;
import cn.sharenotes.db.model.vo.PostSearchVo;
import org.springframework.lang.Nullable;

import java.util.List;

public interface PostService {

    Iterable<PostsIndex> findAll(@Nullable String var1);

    List<PostSearchVo> postSearchVoTransfer(List<PostsIndex> postsIndices);
}
