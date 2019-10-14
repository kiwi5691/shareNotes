package cn.sharenotes.core.redis.KeyPrefix;

import cn.sharenotes.core.redis.KeyPrefix.base.BasePrefix;

/**
 * @auther kiwi
 * @date 2019/6/29 16:00
 */
public class WxAccessTokenKey extends BasePrefix {
    //缺省2hours
    public static final int BOARD_EXPIRE = 3600*2;

    /**
     * 防止被外面实例化
     */
    private WxAccessTokenKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    /**
     * 需要缓存的字段
     */
    public static WxAccessTokenKey board = new WxAccessTokenKey(BOARD_EXPIRE,"wxAccessTokenKey");
}
