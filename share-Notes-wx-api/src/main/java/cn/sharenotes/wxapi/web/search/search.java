package cn.sharenotes.wxapi.web.search;

import cn.sharenotes.core.service.PostContentService;
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

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class search {


    @Autowired
    private PostService postService;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private PostContentService postContentService;

    @GetMapping("/test/search")
    public String searchPosts(){
        //
        List<PostsWithBLOBs> postsWithBLOBsList = new ArrayList<>();
        postContentService.listPostsWithBLOBs();
        postsWithBLOBsList.forEach(postsWithBLOBs -> log.info(postsWithBLOBs.toString()));
        return String.valueOf(postService.findAll("fuck"));

    }


    @GetMapping("/test/createIndex")
    public String createIndex(){

        try {
            elasticsearchTemplate.createIndex(PostsWithBLOBs.class);
            return "success";
        }catch (Exception e){
            log.error("ex:"+e);
            return "fail";
        }

    }



}
