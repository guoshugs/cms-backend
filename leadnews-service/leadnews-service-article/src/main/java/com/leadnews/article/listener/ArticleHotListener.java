package com.leadnews.article.listener;

import com.alibaba.fastjson.JSON;
import com.leadnews.article.dto.ArticleVisitStreamMess;
import com.leadnews.article.service.ApArticleService;
import com.leadnews.common.constants.BC;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @version 1.0
 * @description 说明
 * @package com.leadnews.article.listener
 */
@Component
@Slf4j
public class ArticleHotListener {

    @Resource
    private ApArticleService apArticleService;

    @KafkaListener(topics = BC.MqConstants.HOT_ARTICLE_INCR_HANDLE_TOPIC)
    public void consumeMsg(ConsumerRecord<String,String> msg){
        int p = msg.partition();
        long o = msg.offset();
        String message = msg.value();
        log.debug("文章行为数据更新: p={},o={},v={}", p,o,message);
        ArticleVisitStreamMess articleVisitStreamMess = JSON.parseObject(message, ArticleVisitStreamMess.class);
        apArticleService.computeAndSave2RedisSingle(articleVisitStreamMess);
        log.debug("文章行为数据更新完成: p={},o={},v={}", p,o,message);
    }
}
