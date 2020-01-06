package cn.sharenotes.wxapi.system.dao;

import cn.sharenotes.wxapi.system.dao.base.BaseRepo;
import cn.sharenotes.wxapi.system.entity.SystemConst;
import org.springframework.stereotype.Repository;

/**
 * @author kiwi
 * @date 2019/11/24 11:27
 */
@Repository("systemConstDao")
public interface SystemConstDao extends BaseRepo<SystemConst> {
}
