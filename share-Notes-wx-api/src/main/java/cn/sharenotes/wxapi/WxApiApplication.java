package cn.sharenotes.wxapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"cn.sharenotes.db", "cn.sharenotes.core", "cn.sharenotes.wxapi"})
@EnableTransactionManagement
public class WxApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(WxApiApplication.class, args);
    }

}