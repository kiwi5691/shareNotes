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
        SystemConst systemConst = new SystemConst();
        systemConst.setIsInit("Y");
        systemConst.setConstName("elasticSearch索引库");
        systemConst.setCreatedTs(new Date());
        systemConst.setCreator((long) 1357902468);
        systemConst.setEditor((long) 1357902468);
        systemConst.setLastModifiedTs(new Date());
        systemConstDao.save(systemConst);
        elasticsearchTemplate.createIndex(PostsWithBLOBs.class);
//        systemConstDao.
        log.info("elasticSearch index init ",PostsWithBLOBs.class.getName());
    }
}
