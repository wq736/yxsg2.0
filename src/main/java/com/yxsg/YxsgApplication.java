package com.yxsg;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ImportResource(locations = {"classpath:kaptcha.xml"})
@SpringBootApplication
public class YxsgApplication {

    public static void main(String[] args) {
        SpringApplication.run(YxsgApplication.class, args);
    }

}
