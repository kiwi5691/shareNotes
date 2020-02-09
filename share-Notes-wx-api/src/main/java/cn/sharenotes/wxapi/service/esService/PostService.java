package cn.sharenotes.wxapi.service.esService;

import cn.sharenotes.db.domain.Posts;
import cn.sharenotes.db.domain.PostsWithBLOBs;
import cn.sharenotes.db.domain.index.PostsIndex;
import cn.sharenotes.db.model.dto.PageSearchDto;
import cn.sharenotes.db.model.vo.PostSearchVo;
import io.swagger.models.auth.In;
import org.springframework.data.domain.Page;
import org.springframework.lang.Nullable;

import java.util.List;

public interface PostService {

    Page<PostsIndex> findAll(PageSearchDto pageSearchDto);

    List<PostSearchVo> postSearchVoTransfer(List<PostsIndex> postsIndices);

    Integer accordingPostIdGetUserId(Integer id);

    Integer getCatedByPostId(Integer id);
}
