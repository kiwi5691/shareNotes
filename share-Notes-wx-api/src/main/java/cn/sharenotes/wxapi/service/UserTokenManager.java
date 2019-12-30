package cn.sharenotes.wxapi.service;


import cn.sharenotes.core.utils.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 维护用户token
 */
public class UserTokenManager {
    //定义JWT的有效时长
    private static final int TOKEN_VAILDITY_TIME = 30; // 有效时间(分钟)
    //定义允许刷新JWT的有效时长(在这个时间范围内，用户的JWT过期了，不需要重新登录，后台会给一个新的JWT给前端，这个叫Token的刷新机制后面会着重介绍它的意义。)
    private static final int ALLOW_EXPIRES_TIME = 60*24; //  允许过期时间(分钟)


//    //刷新Token
//    public static String getRefreshToken(String secretKey, DecodedJWT jwtToken) {
//        return getRefreshToken(secretKey, jwtToken, TOKEN_VAILDITY_TIME);
//    }

//    public static String getRefreshToken(String secretKey, DecodedJWT jwtToken, int validityTime, int allowExpiresTime) {
//        Instant now = Instant.now();
//        Instant exp = jwtToken.getExpiresAt().toInstant();
//        //如果当前时间减去JWT过期时间，大于可以重新申请JWT的时间，说明不可以重新申请了，就得重新登录了，此时返回null，否则就是可以重新申请，开始在后台重新生成新的JWT。
//        if ((now.getEpochSecond()-exp.getEpochSecond())>allowExpiresTime*60) {
//            return null;
//        }
//        Algorithm algorithm = null;
//        try {
//            algorithm = Algorithm.HMAC256(secretKey);
//        } catch (IllegalArgumentException | UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        //在原有的JWT的过期时间的基础上，加上这次的有效时间，得到新的JWT的过期时间
//        Instant newExp = exp.plusSeconds(60*validityTime);
//        //创建JWT
//        this.generateToken(id);
//        logger.trace("create refresh token ["+token+"]; iat: "+Date.from(exp)+" exp: "+Date.from(newExp));
//        return token;
//    }

    public static String generateToken(Integer id) {
        JwtHelper jwtHelper = new JwtHelper();
        return jwtHelper.createToken(id);
    }
    public static Integer getUserId(String token) {
        JwtHelper jwtHelper = new JwtHelper();
        Integer userId = jwtHelper.verifyTokenAndGetUserId(token);
        if(userId == null || userId == 0){
            return null;
        }
        return userId;
    }

}
