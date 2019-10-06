package cn.sharenotes.core.redis.KeyPrefix;

import cn.sharenotes.core.redis.KeyPrefix.base.BasePrefix;

/**
 * @auther kiwi
 * @date 2019/6/29 16:00
 */
public class OwnerContentKey extends BasePrefix {
    //默认一天
    public static final int BOARD_EXPIRE = 3600*24 *1;

    /**
     * 防止被外面实例化
     */
    private OwnerContentKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    /**
     * 需要缓存的字段
     */
    public static OwnerContentKey board = new OwnerContentKey(BOARD_EXPIRE,"ownerContentKey");
}
