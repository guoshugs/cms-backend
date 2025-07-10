package com.leadnews.sync.task;

import com.leadnews.search.document.ArticleInfoDocument;
import com.leadnews.sync.mapper.ArticleMapper;
import com.leadnews.sync.repository.DocumentRepository;
import com.leadnews.sync.util.SyncUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;


@Component
@Slf4j
public class SyncTask {

    @Autowired
    private DocumentRepository repository;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 每隔30S执行一次  这个可以根据不同的业务场景选择不同的时间点
     */
    @Scheduled(cron = "0/30 * * * * ?")
    public void taskExecutor() {
        String recordTime = stringRedisTemplate.opsForValue()
                .get("recordTime");
        if (StringUtils.isEmpty(recordTime)) {
            log.info("执行定时任务taskExecutor: 全量同步未执行,此次不执行");
            return;
        }
        if(SyncUtil.sync_status){
            log.info("执行定时任务taskExecutor: 全量同步已执行,但未完成，此次不执行");
            return;
        }
        log.info("执行定时任务taskExecutor:: 执行时间" + LocalDateTime.now());
        //格式化数据
        LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());
        // 设置增量运行的时间，以后要去查询，上次同步到当前的时间范围有变化的。
        stringRedisTemplate.opsForValue().set("recordTime", now.format(SyncUtil.FORMATTER_YMDHMS));
        LocalDateTime redisTime = LocalDateTime.parse(recordTime, SyncUtil.FORMATTER_YMDHMS);
        List<ArticleInfoDocument> apArticles = articleMapper.selectForCondition(redisTime, now);
        log.info("执行定时任务taskExecutor:: 需要同步的文章size={}, from={},to={}", apArticles==null?0:apArticles.size(),redisTime,now);
        if (apArticles != null && apArticles.size() > 0) {
            repository.saveAll(apArticles);// repository 是jpa的方式，saveall是批量导入
        }
    }
}
