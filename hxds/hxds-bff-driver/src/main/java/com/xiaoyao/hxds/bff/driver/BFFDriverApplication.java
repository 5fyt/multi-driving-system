package com.xiaoyao.hxds.bff.driver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class BFFDriverApplication {
    public static void main(String[] args) {
        SpringApplication.run(BFFDriverApplication.class, args);
    }
}
