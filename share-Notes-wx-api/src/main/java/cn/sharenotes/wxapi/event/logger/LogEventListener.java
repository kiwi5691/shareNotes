package cn.sharenotes.wxapi.event.logger;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 *
 * @author kiwi
 */
@Component
public class LogEventListener {

// @autowird   private LogService logService;

    public LogEventListener(/* logservice */) {
//        this.logService = logService;
    }

    @EventListener
    @Async
    public void onApplicationEvent(LogEvent event) {

        //从vo获取值，传入event
        // 存入
    }
}
