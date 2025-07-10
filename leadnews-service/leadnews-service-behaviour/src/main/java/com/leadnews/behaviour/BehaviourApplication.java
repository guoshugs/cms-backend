package com.leadnews.behaviour;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @version 1.0
 * @description 描述
 * @package com.leadnews
 */
@SpringBootApplication
@MapperScan(basePackages = "com.leadnews.behaviour.mapper")
public class BehaviourApplication {
    public static void main(String[] args) {
        SpringApplication.run(BehaviourApplication.class,args);
    }
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}