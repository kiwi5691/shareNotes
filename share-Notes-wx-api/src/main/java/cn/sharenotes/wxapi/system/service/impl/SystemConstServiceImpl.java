package cn.sharenotes.wxapi.system.service.impl;

import cn.sharenotes.core.service.PostContentService;
import cn.sharenotes.db.domain.PostsWithBLOBs;
import cn.sharenotes.db.domain.index.PostsIndex;
import cn.sharenotes.db.repository.PostsIndexRepository;
import cn.sharenotes.db.utils.DtoUtils;
import cn.sharenotes.wxapi.system.dao.SystemConstDao;
import cn.sharenotes.wxapi.system.entity.SystemConst;
import cn.sharenotes.wxapi.system.service.SystemConstService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author kiwi
 * @date 2019/11/24 11:39
 */
@Slf4j
@Service("systemConstService")
public class SystemConstServiceImpl implements SystemConstService {
    @Autowired
    private PostsIndexRepository postsIndexRepository;
    @Autowired
    private PostContentService postContentService;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private SystemConstDao systemConstDao;

    @Override
    public void initEs() {
        Boolean isInit= false;
        List<SystemConst> systemConsts= (List<SystemConst>) systemConstDao.findAll();
        if(systemConsts.size()!=0) {
            systemConsts.stream().forEach(systemConst -> {
                if (systemConst.getConstName().equals("elasticSearch索引库") && systemConst.getIsInit().equals("Y")) {
                    return;
                } else {
                    initedES();
                }
            });
        }else {
            initedES();
        }
    }
    public void initedES() {

        try{

            List<PostsIndex> postsIndices = new ArrayList<>();
            List<PostsWithBLOBs> postsWithBLOBs = new ArrayList<>();
            postsWithBLOBs= postContentService.listPostsWithBLOBs();
            for (PostsWithBLOBs p: postsWithBLOBs) {
                if(p!=null){
                PostsIndex postsIndex = new PostsIndex();

                DtoUtils.copyProperties(p, postsIndex);
                postsIndices.add(postsIndex);
                }
            }

            elasticsearchTemplate.createIndex(PostsIndex.class);
            if(postsIndices.size()!=0){
            postsIndexRepository.saveAll(postsIndices);
            }
            SystemConst systemConst = new SystemConst();
            systemConst.setIsInit("Y");
            systemConst.setConstName("elasticSearch索引库");
            systemConst.setCreatedTs(new Date());
            systemConst.setCreator((long) 1357902468);
            systemConst.setEditor((long) 1357902468);
            systemConst.setLastModifiedTs(new Date());
            systemConstDao.save(systemConst);
            log.info("elasticSearch index init "+PostsIndex.class.getName());

        }catch (Exception e){
            log.error("ElasticSearch无法连接，具体错误："+e);
        }

    }
}
