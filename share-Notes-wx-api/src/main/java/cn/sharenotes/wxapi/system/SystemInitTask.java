package cn.sharenotes.wxapi.system;

import cn.sharenotes.wxapi.system.service.SystemConstService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@Order(1)
public class SystemInitTask implements ApplicationRunner {

    @Autowired
    private SystemConstService systemConstService;

    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(
            1);

    @Override
    public void run(ApplicationArguments args)
            throws Exception
    {
        // 参数：1、任务体 2、首次执行的延时时间
        //      3、任务执行间隔 4、间隔时间单位
        scheduledExecutorService.scheduleAtFixedRate(new Runnable()
        {
            @Override
            public void run()
            {
                systemConstService.initEs();
            }
        }, 0, 3, TimeUnit.SECONDS);

        log.info("inited.");
    }


}
