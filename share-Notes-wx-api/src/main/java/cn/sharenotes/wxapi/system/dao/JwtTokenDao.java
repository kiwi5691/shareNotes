package cn.sharenotes.wxapi.system.dao;

import cn.sharenotes.wxapi.system.dao.base.BaseRepo;
import cn.sharenotes.wxapi.system.dao.base.BaseRepoInteger;
import cn.sharenotes.wxapi.system.entity.JwtToken;
import cn.sharenotes.wxapi.system.entity.SystemConst;
import org.springframework.stereotype.Repository;

/**
 * @author kiwi
 * @date 2019/11/24 11:27
 */
@Repository("jwtTokenDao")
public interface JwtTokenDao extends BaseRepoInteger<JwtToken> {
}
