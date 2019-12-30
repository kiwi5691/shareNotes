package cn.sharenotes.wxapi.web.search;

import cn.sharenotes.db.domain.Posts;
import cn.sharenotes.db.domain.PostsWithBLOBs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class search {


    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;


    @GetMapping("/test/search")
    public String search(){
        elasticsearchTemplate.createIndex(PostsWithBLOBs.class);

    return "fuck";

    }
}
