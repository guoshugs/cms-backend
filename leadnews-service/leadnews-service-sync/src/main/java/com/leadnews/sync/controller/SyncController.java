package com.leadnews.sync.controller;

import com.leadnews.common.vo.ResultVo;
import com.leadnews.sync.service.ArticleService;
import com.leadnews.sync.util.SyncUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.CountDownLatch;
/* 开启服务只能自动增量同步。全量同步是系统管理员在一开始执行的：
* http://localhost:9009/sync/importAll*/

@RestController
@RequestMapping("/sync")
@Slf4j
public class SyncController {
    @Autowired
    private ArticleService articleService;
    //这个可以调整
    private static final Long size = 5000L;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private String endTimeStr = null;



    //多线程方式进行导入
    /* 全量同步是由后台的admin运维人员在部署的时候全量同步触发一次就可以了。*/
    /**
     * 此方法 需要拥有超级管理员的身份人进行操作
     *
     * @return
     */
    @GetMapping("/importAll")
    public ResultVo importAll() {

        Long startTime = System.currentTimeMillis();
        LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());
        String startTimeStr = now.format(SyncUtil.FORMATTER_YMDHMS);
        log.info("全量同步start：{}",startTimeStr);
        // 去redis设置同步时间，使用了setIfAbsent
        Boolean flag = stringRedisTemplate.opsForValue()
                // setIfAbsent相当于redis中的命令setnx:如果key不存在，则设置成功true。如果key存在，则设置失败false
                .setIfAbsent("recordTime",
                    startTimeStr);
        log.info("全量同步start：{}, redisFlag={}",startTimeStr, flag);
        if (flag) {
            try {
                /* 这是静态可视的属性，在类已被加载就生效的，且各个线程都可视，且保证代码有序。
                * 在给这个变量赋值的时候单个线程内部执行顺序不会乱。在类的生命周期任何时候都可以访问到这个变量。*/
                // 标志着全量同步开始
                SyncUtil.sync_status = true;
                //1.查询统计当前需要同步的文章的所有数量
                Long total = articleService.selectCount(now);//因为有时区，所以只能按照记录的时间，不能自己算时间
                Long totalPages = total % size > 0 ? (total / size) + 1 : total / size;
                final CountDownLatch countDownLatch = new CountDownLatch(totalPages.intValue());
                //2.执行多线程方法

                for (Long page = 1L; page <= totalPages; page++) {
                    // 每一页一个线程去跑，用上了异步线程，每个线程完成时加了一个线程计数器，如果所有线程都执行完了，那么await就被唤醒。期间一直等待，直到countDownLatch中的数量减为0
                    // 因为countDownLatch在创建时就指定了，与总页码数相同。
                    articleService.importAll(page, size, countDownLatch,now);
                }
                //3.设置等待 等到 CountDownLatch中的数量减为0之后执行
                try {
                    countDownLatch.await(); // 如果有10页就起10个线程去跑，直到锁减为0
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                SyncUtil.sync_status = false;
            }
            catch (Exception e){
                log.error("全量同步error：{}, redisFlag={}",startTimeStr, flag, e);
                return ResultVo.bizError("发生异常了: " + e.getMessage());
            }
            Long endTime = System.currentTimeMillis();
            endTimeStr = now.format(SyncUtil.FORMATTER_YMDHMS);
            log.info("全量同步end：{}, redisFlag={},cost={}",startTimeStr, flag,(endTime - startTime));
        } else {
            return ResultVo.ok("正在执行中....");
        }
        return ResultVo.ok("全量同步已完成了,start=" + startTimeStr + ",end=" + endTimeStr);
        // 运行期间不能做增量同步
    }

    /* 同步方式有4种，同步调用、异步调用，用定时任务，rabbitmq来同步数据（这里只能用一个交换机一个队列，不管是增删改都是一个方法，就是全量保存。
    * 如果记录存在，就是更新，不存在就是添加。删除数据是逻辑删除。*/

    public static void main(String[] args) {
        String format = LocalDateTime.now(ZoneId.systemDefault()).format(SyncUtil.FORMATTER_YMDHMS);
        System.out.println(format);
    }
}
