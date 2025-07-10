package com.leadnews;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @version 1.0
 * @description 说明
 * @package com.leadnews
 */
@SpringBootApplication
@MapperScan(basePackages = "com.leadnews.search.mapper")
@EnableFeignClients(basePackages = "com.leadnews.*.feign")
@EnableAsync /*开启异步线程查询redis，查询搜索记录*/
public class SearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(SearchApplication.class,args);
    }

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    // 配置线程池
    @Bean(name = "searchWordsPool")// 由spring容器控制，在容器销毁的时候，线程池销毁，此时绑定在线程池的对象，上次请求干的活留存的对象，才销毁。但是里面存放的threadlocal会不变增多。
    public TaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        // 核心线程数该怎么设置？按照业务分类：1、如果业务是密集型：加密、压缩，那么核心线程数就是CPU核数+1；2、IO型，读写数据，包含网络传输，从IO流中读写数据，核心数就是CPU密集型*2
        // 阿里不允许用自带的静态方法的线程池。因为阻塞队列的线程、任务请求进来，可能会把内存压垮溢出，允许的核心线程数有21亿（一个线程大小是1M）
        // 重要业务要独享线程池，因为次要逻辑会拖垮主要逻辑
        executor.setCorePoolSize(20);
        executor.setMaxPoolSize(40);
        executor.setQueueCapacity(200);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("searchWordsExecutor-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setWaitForTasksToCompleteOnShutdown(true);//等所有任务都执行完才关闭

        executor.initialize();
        return executor;
    }
}
