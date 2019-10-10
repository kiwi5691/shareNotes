package cn.sharenotes.wxapi.aspect;

import cn.sharenotes.db.domain.Logs;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * AOP 记录用户操作日志
 *
 * @author kiwi
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

//    @Autowired
//    private LogService logService;
//
//    @Pointcut("@annotation(cn.sharenotes.wxapi.annotation.Log)")
//    public void pointcut() {
//        // do nothing
//    }
//
//    @Around("pointcut()")
//    public Object around(ProceedingJoinPoint point) throws Throwable {
//        Object result;
//        long beginTime = System.currentTimeMillis();
//        // 执行方法
//        result = point.proceed();
//        // 执行时长(毫秒)
//        long time = System.currentTimeMillis() - beginTime;
//            // 保存日志
//            User user =  get user;
//             Logs log = new Logs();
//            if (user != null) {
//                log.setUserId(user.getUserName());
//            }
//            log.setTime(time);
//            logService.saveLog(point, log);
//
//        return result;
//    }
}
