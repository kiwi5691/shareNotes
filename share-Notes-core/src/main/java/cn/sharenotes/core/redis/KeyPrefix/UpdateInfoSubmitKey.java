package cn.sharenotes.core.redis.KeyPrefix;

import cn.sharenotes.core.redis.KeyPrefix.base.BasePrefix;

/**
 * @auther kiwi
 * @date 2019/6/29 16:00
 */
public class UpdateInfoSubmitKey extends BasePrefix {
    //默认一个礼拜
    public static final int BOARD_EXPIRE = 3600*24 *7;

    /**
     * 防止被外面实例化
     */
    private UpdateInfoSubmitKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    /**
     * 需要缓存的字段
     */
    public static UpdateInfoSubmitKey board = new UpdateInfoSubmitKey(BOARD_EXPIRE,"updateInfoSubmitKey");
}
