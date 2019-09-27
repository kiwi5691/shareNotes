package cn.sharenotes.core;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"cn.sharenotes.db", "cn.sharenotes.core"})
@MapperScan("cn.sharenotes.db.mapper")
public class CoreDbApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoreDbApplication.class, args);
    }

}