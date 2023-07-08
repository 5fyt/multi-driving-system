package com.xiaoyao.hxds.bff.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class BFFCustomerApplication {
    public static void main(String[] args) {
        SpringApplication.run(BFFCustomerApplication.class, args);
    }
}
