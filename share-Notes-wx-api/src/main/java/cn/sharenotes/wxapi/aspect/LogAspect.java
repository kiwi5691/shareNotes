package cn.sharenotes.wxapi.aspect;

import cn.sharenotes.core.service.LogService;
import cn.sharenotes.db.domain.Logs;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * AOP 记录用户操作日志
 *
 * @author kiwi
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    @Autowired
    private LogService logService;

    @Pointcut("@annotation(cn.sharenotes.wxapi.annotation.Log)")
    public void pointcut() {
        // do nothing
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result;
        // 执行方法
        result = point.proceed();
        // 保存日志
        Logs log = new Logs();
//        log.setContent();
//        log.setUserId();
        log.setCreateTime(new Date());
        log.setUpdateTime(new Date());
        logService.addLog(log);

        return result;
    }
}
