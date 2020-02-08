package cn.sharenotes.wxapi.web.search;

import cn.sharenotes.core.utils.JacksonUtil;
import cn.sharenotes.core.utils.ResponseUtil;
import cn.sharenotes.db.domain.Posts;
import cn.sharenotes.db.domain.PostsWithBLOBs;
import cn.sharenotes.db.domain.index.PostsIndex;
import cn.sharenotes.db.mapper.UserMapper;
import cn.sharenotes.db.model.dto.PageSearchDto;
import cn.sharenotes.db.model.vo.PostSearchVo;
import cn.sharenotes.wxapi.service.esService.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequestMapping("/wx")
@RestController
public class search {

    public final String failRequest ="fail";
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PostService postService;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;


    @PostMapping("/search/all")
    public Object searchPosts(@Nullable @RequestBody String body){

        PageSearchDto pageSearchDto = bodyBuilder(body);
        if(pageSearchDto!=null){

        Page<PostsIndex> posts =postService.findAll(pageSearchDto);
        List<PostsIndex> postsIndices = posts.get().collect(Collectors.toList());
        List<PostSearchVo> postSearchVos = new ArrayList<>();
        postSearchVos = postService.postSearchVoTransfer(postsIndices);
        Map<String, Object> result = new HashMap<>();
        if(postSearchVos.size()==0)
            result.put("status", 0);
        else {
            result.put("status", postSearchVos.size());
        }
        result.put("data", postSearchVos);
        return ResponseUtil.ok(result);
        }else {
            return ResponseUtil.fail(999,"请输入搜索条件");
        }
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

    public PageSearchDto bodyBuilder(String body){
        PageSearchDto pageSearchDto = new PageSearchDto();

        if (JacksonUtil.parseInteger(body, "page")!=null) {
            pageSearchDto.setPage(JacksonUtil.parseInteger(body, "page"));
        }else {
            pageSearchDto.setPage(0);
        }
        if (JacksonUtil.parseInteger(body, "pageSize")!=null) {
            pageSearchDto.setPageSize(JacksonUtil.parseInteger(body, "pageSize"));
        }else {
            pageSearchDto.setPageSize(10);
        }
        if (!JacksonUtil.parseString(body, "setSearchKeyword").equals("")) {
            String keyWords = JacksonUtil.parseString(body, "setSearchKeyword");
            pageSearchDto.setSearchKeyword(keyWords);
        }else {
            return  null;
        }

        return pageSearchDto;
    }

}
