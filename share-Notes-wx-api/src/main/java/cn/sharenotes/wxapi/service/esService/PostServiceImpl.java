package cn.sharenotes.wxapi.service.esService;

import cn.sharenotes.db.domain.Posts;
import cn.sharenotes.db.domain.PostsWithBLOBs;
import cn.sharenotes.db.repository.PostsWithBLOBsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PostServiceImpl implements PostService{

    @Autowired
    private PostsWithBLOBsRepository postsWithBLOBsRepository;
    @Override
    public Iterable<PostsWithBLOBs> findAll(String var1) {
        Iterable<PostsWithBLOBs> postsWithBLOBs = this.postsWithBLOBsRepository.findAll(Sort.by("updateTime").ascending());
        for (PostsWithBLOBs p: postsWithBLOBs) {
            log.info(p.toString());
        }
        return postsWithBLOBs;
    }
}
