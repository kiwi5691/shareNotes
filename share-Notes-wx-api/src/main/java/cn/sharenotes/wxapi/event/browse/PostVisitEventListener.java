package cn.sharenotes.wxapi.event.browse;

import cn.sharenotes.wxapi.service.PostService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 浏览监听事件
 *
 * @author kiwi
 * @date 19-4-22
 */
@Component
public class PostVisitEventListener extends AbstractVisitEventListener {

    public PostVisitEventListener(PostService postService) {
        super(postService);
    }

    @Async
    @EventListener
    public void onPostVisitEvent(PostVisitEvent event) throws InterruptedException {
        handleVisitEvent(event);
    }
}
