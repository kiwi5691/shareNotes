package cn.sharenotes.wxapi.service;

import cn.binarywang.wx.miniapp.api.WxMaSecCheckService;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.sharenotes.core.redis.KeyPrefix.WxAccessTokenKey;
import cn.sharenotes.core.redis.RedisManager;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * @author kiwi
 * @date 2019/10/14 15:40
 * 内容合法验证
 */
public class SecCheck {

    @Autowired
    private WxMaService wxMaService;
    @Autowired
    private WxMaSecCheckService wxMaSecCheckService;
    @Resource
    private RedisManager redisManager;


    public String getAccessToken() throws WxErrorException {
        String token =null;
        token =redisManager.get(WxAccessTokenKey.board,"wx",String .class);
        if (StringUtils.isBlank(token)) {
            token= wxMaService.getAccessToken();
            redisManager.set(WxAccessTokenKey.board,"wx",token);
        }
        return token;
    }

//    public
}
