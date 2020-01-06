package cn.sharenotes.wxapi.service.esService;

import cn.sharenotes.db.domain.Posts;
import cn.sharenotes.wxapi.service.esService.respo.postRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    private cn.sharenotes.wxapi.service.esService.respo.postRepository postRepository;
    @Override
    public List<Posts> findAll(String var1) {
        List<Posts> list = (List<Posts>) this.postRepository.findAll(Sort.by("createTime").ascending());

        return list;
    }
}
