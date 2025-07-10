package com.leadnews.admin.listener;

import com.leadnews.admin.service.WemediaNewsAutoScanService;
import com.leadnews.common.constants.BC;
import com.leadnews.common.exception.LeadNewsException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;

@Component
@Slf4j
public class WemediaNewsAutoScanListener {
    @Resource
    private WemediaNewsAutoScanService wemediaNewsAutoScanService;

    /**
     * 文章自动审核
     * @param msg
     */
    @KafkaListener(topics = BC.MqConstants.WM_NEWS_AUTO_SCAN_TOPIC)
    public void autoScanWemediaNews(ConsumerRecord<String,String> msg){
        // 消息所在分区
        int p = msg.partition();
        // 消息对应的offset
        long o = msg.offset();
        // 消息内容
        String msgValue = msg.value();
        // 唯一id，用于标记日志是同一次业务，因为日志是异步线程打印的，同一个内容。不同流程打同一个uuid，日志就在一起了，追踪它的流程，uuid相同的代表同一个业务。
        String uuid = UUID.randomUUID().toString();
        log.debug("开始文章的自动审核: partition={},offset={},msg={},uuid={}", p,o,msgValue,uuid);
        // 解析文章id
        long wmNewsId = Long.parseLong(msgValue);
        // 业务代码
        try {
            // 文章自动审核
            wemediaNewsAutoScanService.autoScanWemediaNewsById(wmNewsId, uuid); // 不能只传news_id，如果部署了2~3个消费者admin，如果多个消费者，有可能集中在一起
        } catch (Exception e) {
            log.error("文章自动审核失败!", e);
            // 这个Listener已经进入到了所在微服务的外层了，但这里仍然抛了异常。主要是由于用mq做了兜底。
            // 指的是rabbitMq。这里用的kafka是不符合兜底的业务场景的。因为：
            /**
             * Kafka中的消息重试通常与消费者组合使用，而死信队列（Dead Letter Queue，DLQ）通常与消息中间件如 RabbitMQ 或 ActiveMQ 等关联更多。
             * 在 Kafka中，消息的重试是由消费者控制的，当消费者在处理消息时发生错误或者处理超时时，可以选择重试消息。
             * 如果消费者在重试次数耗尽后仍然无法成功处理消息，Kafka 并没有直接提供将消息放入死信队列的机制。
             */
            // 发邮件、短信通知运维人员处理
            // 如果使用RabbitMQ, 重试次数耗尽、能够进入死信，才需要人工处理。
            throw new LeadNewsException(String.format("文章自动审核失败!uuid=%s,wmNewsId=%d", uuid,wmNewsId));
        }
        log.debug("文章的自动审核完成: partition={},offset={},msg={},uuid={}", p,o,msgValue,uuid);
    }
}
