package com.cx.springboot02;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(scanBasePackages={"com.cx.springboot02.*"})
@MapperScan(basePackages = "com.cx.springboot02.mapper")
public class SSMPApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(SSMPApplication.class, args);
    }
}
