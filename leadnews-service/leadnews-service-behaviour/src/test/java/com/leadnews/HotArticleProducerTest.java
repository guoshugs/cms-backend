package com.leadnews;

import com.alibaba.fastjson.JSON;
import com.leadnews.behaviour.dto.UpdateArticleMess;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @description 说明
 * @package com.leadnews
 */
@SpringBootTest
public class HotArticleProducerTest {

    @Resource
    private KafkaTemplate kafkaTemplate;
    String  HOT_ARTICLE_SCORE_TOPIC="article_behavior_input";

    @Test
    public void testStream(){
        UpdateArticleMess msg = null;
        //【注意】这里的id改成你leadnews_article.ap_article.id
        long articleId = 1779253481031471105l;
        //long articleId = 1777863057683386370l;
        //long articleId = 1777864567678963714l;
        List<String> list = new ArrayList<>();
        // 10个点赞
        for (int i = 0; i < 10; i++) {
            msg = new UpdateArticleMess();
            msg.setArticleId(articleId);
            msg.setType(UpdateArticleMess.UpdateArticleType.LIKES);
            msg.setNum(1);
            list.add(JSON.toJSONString(msg));
        }
        // 20个阅读
        for (int i = 0; i < 20; i++) {
            msg = new UpdateArticleMess();
            msg.setArticleId(articleId);
            msg.setType(UpdateArticleMess.UpdateArticleType.VIEWS);
            msg.setNum(1);
            list.add(JSON.toJSONString(msg));
        }
        //5个评论
        for (int i = 0; i < 5; i++) {
            msg = new UpdateArticleMess();
            msg.setArticleId(articleId);
            msg.setType(UpdateArticleMess.UpdateArticleType.COMMENT);
            msg.setNum(1);
            list.add(JSON.toJSONString(msg));
        }
        //1个收藏
        msg = new UpdateArticleMess();
        msg.setArticleId(articleId);
        msg.setType(UpdateArticleMess.UpdateArticleType.COLLECTION);
        msg.setNum(1);
        list.add(JSON.toJSONString(msg));


        for (String json : list) {
            kafkaTemplate.send(HOT_ARTICLE_SCORE_TOPIC, json);
        }
    }
}
