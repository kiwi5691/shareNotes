package cn.sharenotes.wxapi.system.service.impl;

import cn.sharenotes.wxapi.system.dao.JwtTokenDao;
import cn.sharenotes.wxapi.system.dao.SystemConstDao;
import cn.sharenotes.wxapi.system.entity.JwtToken;
import cn.sharenotes.wxapi.system.service.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("jwtTokenService")
public class JwtTokenServiceImpl implements JwtTokenService {
    @Autowired
    private JwtTokenDao jwtTokenDao;


    @Override
    public void save(JwtToken jwtToken) {
        jwtTokenDao.save(jwtToken);
    }
}

