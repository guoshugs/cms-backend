package com.leadnews.article.stream;

import com.alibaba.fastjson.JSON;
import com.leadnews.article.dto.ArticleVisitStreamMess;
import com.leadnews.behaviour.dto.UpdateArticleMess;
import com.leadnews.common.constants.BC;
import com.leadnews.common.exception.LeadNewsException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Aggregator;
import org.apache.kafka.streams.kstream.Initializer;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @description 说明
 * @package com.leadnews.article.stream
 */
@Configuration
@Slf4j
public class HotArticleStreamHandler {

    /**
     * 实时热点文章计算
     *
     * @param streamsBuilder
     * @return
     */
    @Bean
    public KStream<String, String> kStream(StreamsBuilder streamsBuilder) {
        //处理器要先去主题中拿到“输入流”，接下来看如何处理
        KStream<String, String> stream = streamsBuilder.stream(BC.MqConstants.HOT_ARTICLE_SCORE_TOPIC);
        /*通过测试可以看到kafka中发来的消息格式：
         * {"articleId":1777863057683386370,"num":1,"type":2}
         * 有了格式才能知道如何处理
         * 发送消息：kafkaTemplate.send(HOT_ARTICLE_SCORE_TOPIC, JSON.toJSONString(updateArticleMsg));
         * 发送时没有指定key。接收时只接收value，自己再补上key(articleId)，通过map将流中的kv的默认key替换一下。
         * */
        // 处理
        stream.map((key, value) -> { // 我的VIEW方法出现了问题，消息中出现了很多没有articleId的消息。所以就有空指针，要过滤出有articleId的
            //key是null，value就是消息 UpdateArticleMess {"articleId":1777863057683386370,"num":1,"type":2}
            UpdateArticleMess msg = JSON.parseObject(value, UpdateArticleMess.class);
            Long articleId = msg.getArticleId();
            if (null == articleId) {
                throw new LeadNewsException("Long转换异常~~~~~~~~~~~~");
            }
            // key是用来分组用的，key=文章id
            return new KeyValue<String, String>(articleId.toString(), value);
            // 现在仍在visitStream中，即仍在kafka的流处理器中，它里面的序列化和反序列化器都是String类型的，所以KV必须里面必须放String类型
        })
                // 分组
                .groupBy((key, value) -> key)
                // 统计周期，流是一个源源不断。收集这个周期内的消息
                .windowedBy(TimeWindows.of(Duration.ofSeconds(10)))
                // 把这10秒的消息聚合起来了，成了list, 遍历它，聚合统计   .aggreate(new Initializer(), new Aggregator, Materialized.as())
                .aggregate(new Initializer<String>() {
                    /** 聚合的目的是把多个updateMessage转化成一条VisitStreamMessage
                     * 流式变成的initializer放在后面写，是要作为callback回调函数而存在的。懒惰加载的含义。
                     * 消息到了，要聚合才初始化聚合器，而聚合器的存在条件是里面需要一个VisitStreamMessage
                     * 对结果消息 的初始化, 统计周期内只会执行一次
                     * 返回值传给聚合方法 Aggregator.apply第三个参数
                     * @return the initial value for an aggregation
                     */
                    @Override
                    public String apply() {
                        ArticleVisitStreamMess resultMsg = new ArticleVisitStreamMess();
                        return JSON.toJSONString(resultMsg);
                    }
                }, new Aggregator<String, String, String>() {
                    /** 聚合器得会聚合，定制化的聚合功能是暴露给外部的接口
                     * 聚合累加， 收集完消息集合后，遍历消息集合，调用这个方法
                     * 下面的三个参数是key, value, aggregate: public String apply(String key, String value, String aggregate)
                     * aggregate是中间产物聚合结果，聚合器最后返回的也是它！承装聚合结果的容器就是initializer中的VisitStreamMessage。
                     * 所以具体的聚合方法就是按照key，把value中的值计算后放到aggregate中间产物中
                     */
                    @Override
                    public String apply(String articleId, String updateArticleMessJson, String articleVisitStreamMessJson) {
                        // 把value消息转成java对象
                        UpdateArticleMess valMsg = JSON.parseObject(updateArticleMessJson, UpdateArticleMess.class);
                        // 聚合的结果转成java对象
                        ArticleVisitStreamMess midResultMsg = JSON.parseObject(articleVisitStreamMessJson, ArticleVisitStreamMess.class);
                        midResultMsg.setArticleId(Long.parseLong(articleId));
                        // 判断行为类型
                        int type = valMsg.getType().getValue();
                        switch (type) {
                            case 4:
                                midResultMsg.setView(midResultMsg.getView() + valMsg.getNum());
                                break;
                            case 3:
                                midResultMsg.setLike(midResultMsg.getLike() + valMsg.getNum());
                                break;
                            case 2:
                                midResultMsg.setComment(midResultMsg.getComment() + valMsg.getNum());
                                break;
                            case 1:
                                midResultMsg.setCollect(midResultMsg.getCollect() + valMsg.getNum());
                                break;
                        }
                        // 聚合后的结果，返回数据会变成下一次调用时的第三个参数
                        String midResult = JSON.toJSONString(midResultMsg);
                        System.err.println("midResult: " + midResult);
                        return midResult;
                    }
                    // 取别名， 唯一即可
                }, Materialized.as("hotArticleStatistics"))
                // 把聚合过程的KTable转换成KStream
                .toStream()
                // 数据类型转成kafka消息数据类型
                .map((key, value) -> new KeyValue<>(key.key().toString(), value.toString()))
                // 输出主题
                .to(BC.MqConstants.HOT_ARTICLE_INCR_HANDLE_TOPIC);
        //输出
        return stream;
    }
/*--------------------------------------------test---------------------------------------------------------------*/
    /*public static void main(String[] args) {
        List<UpdateArticleMess> dataList = mockData();
        // 初始
        ArticleVisitStreamMess resultMsg = new ArticleVisitStreamMess();
        // 遍历数据
        for (UpdateArticleMess msg : dataList) {
            resultMsg.setArticleId(msg.getArticleId());
            // 判断行为类型
            int type = msg.getType().getValue();
            switch (type){
                case 4: resultMsg.setView(resultMsg.getView() + msg.getNum());break;
                case 3: resultMsg.setLike(resultMsg.getLike() + msg.getNum());break;
                case 2: resultMsg.setComment(resultMsg.getComment() + msg.getNum());break;
                case 1: resultMsg.setCollect(resultMsg.getCollect() + msg.getNum());break;
            }
        }
        System.out.println(resultMsg);
    }*/

    private static List<UpdateArticleMess> mockData() {
        UpdateArticleMess msg = null;
        List<UpdateArticleMess> list = new ArrayList<>();
        // 10个点赞
        for (int i = 0; i < 10; i++) {
            msg = new UpdateArticleMess();
            msg.setArticleId(101l);
            msg.setType(UpdateArticleMess.UpdateArticleType.LIKES);
            msg.setNum(1);
            list.add(msg);
        }
        // 20个阅读
        for (int i = 0; i < 20; i++) {
            msg = new UpdateArticleMess();
            msg.setArticleId(101l);
            msg.setType(UpdateArticleMess.UpdateArticleType.VIEWS);
            msg.setNum(1);
            list.add(msg);
        }
        //5个评论
        for (int i = 0; i < 5; i++) {
            msg = new UpdateArticleMess();
            msg.setArticleId(101l);
            msg.setType(UpdateArticleMess.UpdateArticleType.COMMENT);
            msg.setNum(1);
            list.add(msg);
        }
        //1个收藏
        msg = new UpdateArticleMess();
        msg.setArticleId(101l);
        msg.setType(UpdateArticleMess.UpdateArticleType.COLLECTION);
        msg.setNum(1);
        list.add(msg);
        return list;
    }

    public static void main(String[] args) {
        List<String> dataList = getMockData();
        // 初始
        String resultMsg = init();
        Long articleId = 101l;
        // 遍历数据
        for (String updateArticleMessJson : dataList) {
            resultMsg = aggregate(articleId, updateArticleMessJson, resultMsg);
        }
        System.out.println(resultMsg);
    }

    private static String aggregate(Long articleId, String updateArticleMessJson, String articleVisitStreamMessJson) {
        // 把消息转成java对象
        UpdateArticleMess msg = JSON.parseObject(updateArticleMessJson, UpdateArticleMess.class);
        // 聚合的结果转成java对象
        ArticleVisitStreamMess resultMsg = JSON.parseObject(articleVisitStreamMessJson, ArticleVisitStreamMess.class);
        resultMsg.setArticleId(articleId);
        // 判断行为类型
        int type = msg.getType().getValue();
        switch (type) {
            case 4:
                resultMsg.setView(resultMsg.getView() + msg.getNum());
                break;
            case 3:
                resultMsg.setLike(resultMsg.getLike() + msg.getNum());
                break;
            case 2:
                resultMsg.setComment(resultMsg.getComment() + msg.getNum());
                break;
            case 1:
                resultMsg.setCollect(resultMsg.getCollect() + msg.getNum());
                break;
        }
        // 聚合后的结果，返回
        return JSON.toJSONString(resultMsg);
    }

    private static String init() {
        ArticleVisitStreamMess resultMsg = new ArticleVisitStreamMess();
        return JSON.toJSONString(resultMsg);
    }

    private static List<String> getMockData() {
        UpdateArticleMess msg = null;
        List<String> list = new ArrayList<>();
        // 10个点赞
        for (int i = 0; i < 10; i++) {
            msg = new UpdateArticleMess();
            msg.setArticleId(101l);
            msg.setType(UpdateArticleMess.UpdateArticleType.LIKES);
            msg.setNum(1);
            list.add(JSON.toJSONString(msg));
        }
        // 20个阅读
        for (int i = 0; i < 20; i++) {
            msg = new UpdateArticleMess();
            msg.setArticleId(101l);
            msg.setType(UpdateArticleMess.UpdateArticleType.VIEWS);
            msg.setNum(1);
            list.add(JSON.toJSONString(msg));
        }
        //5个评论
        for (int i = 0; i < 5; i++) {
            msg = new UpdateArticleMess();
            msg.setArticleId(101l);
            msg.setType(UpdateArticleMess.UpdateArticleType.COMMENT);
            msg.setNum(1);
            list.add(JSON.toJSONString(msg));
        }
        //1个收藏
        msg = new UpdateArticleMess();
        msg.setArticleId(101l);
        msg.setType(UpdateArticleMess.UpdateArticleType.COLLECTION);
        msg.setNum(1);
        list.add(JSON.toJSONString(msg));
        return list;
    }
}
