package cn.sharenotes.wxapi.service;

import cn.sharenotes.db.domain.Logs;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author 76905
 */
@Service
public interface LogService {

    /**
     * 异步保存操作日志
     *
     * @param point 切点
     * @param logs   日志
     * @throws JsonProcessingException 异常
     */

    Logs getLogInfo(ProceedingJoinPoint point, Logs logs);

    @Async
    Integer addLog(Logs logs);
}
