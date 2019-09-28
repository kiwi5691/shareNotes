package cn.sharenotes.wxapi.service.Impl;

import cn.sharenotes.wxapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * @author kiwi
 */
@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

        // // TODO: 2019/9/28  加上监听器 来实现 写文章时候自动存储到数据库
}
