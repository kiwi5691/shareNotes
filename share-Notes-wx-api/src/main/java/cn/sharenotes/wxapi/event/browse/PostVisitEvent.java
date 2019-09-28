package cn.sharenotes.wxapi.event.browse;

/**
 * 文章浏览事件.
 *
 * @author kiwi
 */
public class PostVisitEvent extends AbstractVisitEvent {

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     * @param postId post id must not be null
     */
    public PostVisitEvent(Object source, Integer postId) {
        super(source, postId);
    }
}
