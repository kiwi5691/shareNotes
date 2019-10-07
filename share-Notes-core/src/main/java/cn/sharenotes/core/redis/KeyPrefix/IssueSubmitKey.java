package cn.sharenotes.core.redis.KeyPrefix;

import cn.sharenotes.core.redis.KeyPrefix.base.BasePrefix;

/**
 * @auther kiwi
 * @date 2019/6/29 16:00
 */
public class IssueSubmitKey extends BasePrefix {
    //默认半天
    public static final int BOARD_EXPIRE = 3600*12 *1;

    /**
     * 防止被外面实例化
     */
    private IssueSubmitKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    /**
     * 需要缓存的字段
     */
    public static IssueSubmitKey board = new IssueSubmitKey(BOARD_EXPIRE,"issueSubmitKey");
}
