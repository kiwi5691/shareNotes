package cn.sharenotes.core.redis.KeyPrefix;

import cn.sharenotes.core.redis.KeyPrefix.base.BasePrefix;

public class PostCommentKey  extends BasePrefix {
    public PostCommentKey(String prefix) {
        super(prefix);
    }

    public PostCommentKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);

    }
}
