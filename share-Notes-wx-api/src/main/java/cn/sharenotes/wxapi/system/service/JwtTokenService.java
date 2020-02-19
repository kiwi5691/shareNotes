package cn.sharenotes.wxapi.system.service;

import cn.sharenotes.wxapi.system.entity.JwtToken;

public interface JwtTokenService {
    void save(String token,Integer id);
}
