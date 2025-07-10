package com.leadnews;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @version 1.0
 * @description 说明
 * @package com.leadnews
 */
@SpringBootApplication
@EnableFeignClients(basePackages = "com.leadnews.*.feign")
public class DfsApp {

    public static void main(String[] args) {
        SpringApplication.run(DfsApp.class, args);
    }
}
