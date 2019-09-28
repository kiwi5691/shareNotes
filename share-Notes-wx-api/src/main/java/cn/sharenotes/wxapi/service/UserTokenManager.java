package cn.sharenotes.wxapi.service;


/**
 * 维护用户token
 */
public class UserTokenManager {
	public static String generateToken(Integer id) {
//       todo  这里使用redis
//       方法大概 redisTemplate.createToken(id)
	    return "redis TOKEN SET";
    }
    public static Integer getUserId(String token) {

	    Integer userId =0; //TODO redis get
    	if(userId == null || userId == 0){
    		return null;
    	}
        return userId;
    }
}
