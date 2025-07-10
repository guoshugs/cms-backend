package com.leadnews.behaviour.listener;

import com.alibaba.fastjson.JSON;
import com.leadnews.behaviour.dto.FollowBehaviourDto;
import com.leadnews.behaviour.service.ApFollowBehaviorService;
import com.leadnews.common.constants.BC;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class FollowBehaviourListener {
    @Resource
    private ApFollowBehaviorService apFollowBehaviorService;

    @KafkaListener(topics = BC.MqConstants.FOLLOW_BEHAVIOR_TOPIC)
    public void saveFollowBebehaviour(ConsumerRecord<String, String> msgRecord) {
        int p = msgRecord.partition();
        long o = msgRecord.offset();
        String msg = msgRecord.value();
        String topic = msgRecord.topic();

        log.info("用户关注行为 start: topic={}, partition={}, msg={}", topic, p, msg);
        FollowBehaviourDto dto = JSON.parseObject(msg, FollowBehaviourDto.class);
        apFollowBehaviorService.saveFollowBehaviour(dto);
        log.info("用户关注行为 end: topic={}, partition={}, msg={}", topic, p, msg);
    }
}
