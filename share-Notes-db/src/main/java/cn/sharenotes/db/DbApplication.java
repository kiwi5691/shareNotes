package cn.sharenotes.db;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {"cn.sharenotes.db"})
@MapperScan("cn.sharenotes.db.dao")
public class DbApplication {

    public static void main(String[] args) {
        SpringApplication.run(DbApplication.class, args);
    }

}