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

    @Override
    public void run(ApplicationArguments args) throws Exception {

        systemConstService.initEs();
        log.info("inited.");
    }


}
