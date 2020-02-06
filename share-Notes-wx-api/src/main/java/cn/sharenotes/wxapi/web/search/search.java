package cn.sharenotes.wxapi.web.search;

import cn.sharenotes.db.domain.Posts;
import cn.sharenotes.db.domain.PostsWithBLOBs;
import cn.sharenotes.wxapi.service.esService.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class search {


    @Autowired
    private PostService postService;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;


    @GetMapping("/test/search")
    public String searchPosts(){
        Iterable<PostsWithBLOBs> postsWithBLOBs =postService.findAll("");
        StringBuilder builder = new StringBuilder();
        for (PostsWithBLOBs p: postsWithBLOBs) {
            builder.append(p.toString());
            log.info(builder.toString());

        }
        return builder.toString();

    }
    @GetMapping("/test/createIndex")
    public String createIndex(){

        try {
            elasticsearchTemplate.createIndex(PostsWithBLOBs.class);
        }catch (Exception e){

        }
        return "sus";

    }


    @GetMapping("/fuck/{id}")
    public String fuck(@PathVariable("id") String id){

        if(id.equals("1")) {
            return "fuck mother";
        }else if(id.equals("2")){
            return "你麻痹的棒棒糖";
        }
        return "sb";
    }
}
