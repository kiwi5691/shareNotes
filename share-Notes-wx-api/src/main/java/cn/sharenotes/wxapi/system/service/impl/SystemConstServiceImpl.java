package cn.sharenotes.wxapi.system.service.impl;

import cn.sharenotes.core.service.PostContentService;
import cn.sharenotes.db.domain.PostsWithBLOBs;
import cn.sharenotes.db.repository.PostsWithBLOBsRepository;
import cn.sharenotes.wxapi.system.dao.SystemConstDao;
import cn.sharenotes.wxapi.system.entity.SystemConst;
import cn.sharenotes.wxapi.system.service.SystemConstService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
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
    private PostsWithBLOBsRepository postsWithBLOBsRepository;
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
                    initES();
                }
            });
        }else {
            initES();
        }
    }
    public void initES() {

        try{

            List<PostsWithBLOBs> postsWithBLOBsList = new ArrayList<>();
            postsWithBLOBsList= postContentService.listPostsWithBLOBs();
            elasticsearchTemplate.createIndex(PostsWithBLOBs.class);
            if(postsWithBLOBsList.size()!=0){
            postsWithBLOBsRepository.saveAll(postsWithBLOBsList);
            }
            SystemConst systemConst = new SystemConst();
            systemConst.setIsInit("Y");
            systemConst.setConstName("elasticSearch索引库");
            systemConst.setCreatedTs(new Date());
            systemConst.setCreator((long) 1357902468);
            systemConst.setEditor((long) 1357902468);
            systemConst.setLastModifiedTs(new Date());
            systemConstDao.save(systemConst);
            log.info("elasticSearch index init "+PostsWithBLOBs.class.getName());

        }catch (Exception e){
            log.error("ElasticSearch无法连接，具体错误："+e);
        }

    }
}
