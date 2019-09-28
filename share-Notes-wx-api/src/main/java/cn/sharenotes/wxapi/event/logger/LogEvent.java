package cn.sharenotes.wxapi.event.logger;

import org.springframework.context.ApplicationEvent;

/**
 * @author kiwi
 */
public class LogEvent extends ApplicationEvent {


  
    public LogEvent(Object source/*vo */) {
        super(source);

//        Validation vo的参数

    }

// // TODO: 2019/9/28 构造LogEvent    public LogEvent(Object source, String logKey, LogType logType, String content) {
//    }

}
