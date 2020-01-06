package cn.sharenotes.wxapi.system.service.impl;

import cn.sharenotes.db.domain.PostsWithBLOBs;
import cn.sharenotes.wxapi.system.dao.SystemConstDao;
import cn.sharenotes.wxapi.system.entity.SystemConst;
import cn.sharenotes.wxapi.system.service.SystemConstService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private SystemConstDao systemConstDao;

    @Override
    public void initEs() {
        List<SystemConst> systemConsts= (List<SystemConst>) systemConstDao.findAll();
        if(systemConsts!=null) {
            systemConsts.stream().forEach(systemConst -> {
                if (systemConst.getEsInit() == null || systemConst.getEsInit() == "N") {
                    System.out.println(systemConst);
                    init();
                    systemConst.setEsInit("Y");
                } else {
                    log.info("elasticSearch index already established");
                }
            });
        }else {
            SystemConst systemConst = new SystemConst();
            systemConst.setEsInit("Y");
            systemConst.setCreatedTs(new Date());
            systemConst.setCreator((long) 1357902468);
            systemConst.setEditor((long) 1357902468);
            systemConst.setLastModifiedTs(new Date());
            systemConstDao.save(systemConst);
            init();
        }
    }
    public void init() {
        elasticsearchTemplate.createIndex(PostsWithBLOBs.class);
        log.info("elasticSearch index init ",PostsWithBLOBs.class.getName());
    }
}
