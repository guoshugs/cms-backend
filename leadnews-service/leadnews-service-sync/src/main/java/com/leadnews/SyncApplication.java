package com.leadnews;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@SpringBootApplication
@EnableAsync // 开启异步线程支持 @Async异步调用
/* spring的异步多线程使用：
* 在启动类上添加@EnableAsync
* spring中虽然不用做线程池，但是在使用要求规范中，必须配合线程池来使用。所以必须注册线程池到spring容器中。
* 无论使用什么样的注册方式都可以，但是必须要注册一个线程池进去。
* 在需要进行异步调用的方法上添加@Async(value=指定线程池的bean名称)
* */
@MapperScan(basePackages = "com.leadnews.sync.mapper")
@EnableScheduling
/* 执行本地定时任务，需要打3个注解，启动类上打@EnableScheduling，执行器上打@Component，定时任务上打@Scheduled(cron="0/30 * * * * ?")*/
public class SyncApplication {

    public static void main(String[] args) {
        SpringApplication.run(SyncApplication.class,args);
    }

    //配置线程池
    @Bean(name = "taskExecutor")
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置核心线程数
        executor.setCorePoolSize(20);
        // 设置最大线程数
        executor.setMaxPoolSize(40);
        // 设置队列容量
        executor.setQueueCapacity(200);
        // 设置线程活跃时间（秒）
        executor.setKeepAliveSeconds(60);
        // 设置默认线程名称
        executor.setThreadNamePrefix("taskExecutor-");
        // 设置拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);

        executor.initialize();
        return executor;
    }
}
