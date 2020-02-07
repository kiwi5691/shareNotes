package cn.sharenotes.wxapi.web.search;

import cn.sharenotes.core.utils.ResponseUtil;
import cn.sharenotes.db.domain.Posts;
import cn.sharenotes.db.domain.PostsWithBLOBs;
import cn.sharenotes.db.domain.index.PostsIndex;
import cn.sharenotes.db.mapper.UserMapper;
import cn.sharenotes.db.model.vo.PostSearchVo;
import cn.sharenotes.wxapi.service.esService.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RequestMapping("/wx")
@RestController
public class search {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PostService postService;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;


    @GetMapping("/search/all")
    public Object searchPosts(){
        Iterable<PostsIndex> posts =postService.findAll("");
        List<PostsIndex> postsIndices = new ArrayList<>();
        posts.forEach(postsIndices::add);

        List<PostSearchVo> postSearchVos = new ArrayList<>();
        postSearchVos = postService.postSearchVoTransfer(postsIndices);
        Map<String, Object> result = new HashMap<>();
        result.put("data", postSearchVos);
        if(postSearchVos.size()==0)
            result.put("status", 0);
        else {
            result.put("status", postSearchVos.size());
        }
        return ResponseUtil.ok(result);
    }

    @RequestMapping("fuck")
    public Object f(){
        Map<String,Object> h = new HashMap<>();
        h=userMapper.selectNameAndAvatarById(3);

        for (String key : h.keySet()) {
            System.out.println("key= "+ key + " and value= " + h.get(key));
        }
        System.out.println("test:"+h.get("avatar"));
        System.out.println("test:"+h.get("username"));
        return h;
    }

}
