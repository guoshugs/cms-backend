package com.leadnews;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * @version 1.0
 * @description 描述
 * @package com.leadnews
 */
@SpringBootApplication
@MapperScan(basePackages = "com.leadnews.user.mapper")
@EnableFeignClients(basePackages = "com.leadnews.*.feign")
//既然引入feign的接口工程，那就要开启feign扫包，*可以是wemedia、article等写了feign接口的api工程目录
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class,args);
    }

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}