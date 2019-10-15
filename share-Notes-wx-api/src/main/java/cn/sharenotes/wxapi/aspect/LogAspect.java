package cn.sharenotes.wxapi.aspect;

import cn.sharenotes.core.utils.HttpContextUtil;
import cn.sharenotes.db.domain.Logs;
import cn.sharenotes.wxapi.service.LogService;
import cn.sharenotes.wxapi.service.UserTokenManager;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
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

    public static final String LOGIN_TOKEN_KEY = "F-shareNotes-Token";

    @Autowired
    private LogService logService;

    @Pointcut("@annotation(cn.sharenotes.wxapi.annotation.Log)")
    public void pointcut() {
        // do nothing
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        Object result;

        HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
        String token = request.getHeader(LOGIN_TOKEN_KEY);
        if (token == null || token.isEmpty()) {
            return null;
        }

        Integer userId = UserTokenManager.getUserId(token);

        Logs logs = new Logs();
        logs.setUserId(String.valueOf(userId));
        logs.setCreateTime(new Date());
        logs.setUpdateTime(new Date());
        logs = logService.getLogInfo(point, logs);

        //执行时得判断切点是否执行成功
        result = point.proceed();

        logService.addLog(logs);

        return result;
    }
}
