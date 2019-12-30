package cn.sharenotes.wxapi.service;

/**
 * @author kiwi
 * jwt操作
 */
public interface JwtManageService {
    //获取jwtToken
    void getJwtToken(Integer id);
    //jwtToken刷新过期
    void refreshJwtToken();

}
