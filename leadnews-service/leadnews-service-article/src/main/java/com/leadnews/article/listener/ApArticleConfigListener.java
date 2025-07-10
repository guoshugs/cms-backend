package com.leadnews.article.listener;

import com.leadnews.article.service.ApArticleConfigService;
import com.leadnews.common.constants.BC;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class ApArticleConfigListener {
    @Resource
    private ApArticleConfigService apArticleConfigService;

    @KafkaListener(topics = BC.MqConstants.WM_NEWS_DOWN_OR_UP_TOPIC)
    public void consumer(ConsumerRecord<String, String> msg){
        int p = msg.partition();
        long o = msg.offset();
        String message = msg.value();
        log.debug("文章配置消费者更新文章上下架: p={},o={},v={}", p,o,message);
        apArticleConfigService.upOrDown(message);
        log.info("同步文章上下架操作完成：{},{},{},{}", msg.topic(), p, o, message);
    }
}
