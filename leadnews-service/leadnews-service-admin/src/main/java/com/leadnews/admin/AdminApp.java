package com.leadnews.admin;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * @version 1.0
 * @description 没有放在admin包下，而是放在大包下，因为若是启动类放在了子包下，
 * 最后的全局异常处理是无效的。因为全局异常处理是放在了common包下，
 * 启动类所在的包必须包含common包。
 * @package com.leadnews
 */
@SpringBootApplication
// Mybatis mapper扫包
@MapperScan("com.leadnews.admin.mapper")
@EnableFeignClients(basePackages = "com.leadnews.*.feign")
public class AdminApp {
    public static void main(String[] args) {
        SpringApplication.run(AdminApp.class,args);
    }

    // Mybatis Plus 分页拦截器，因为mybatisplus的版本比较老，所以要手动添加
    // 分页插件必须添加，不然查询就会全部爆出来
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }
}