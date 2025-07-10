package com.leadnews.article.service.impl;

import com.alibaba.fastjson.JSON;
import com.leadnews.article.dto.CollectionBehaviorDto;
import com.leadnews.article.pojo.ApCollection;
import com.leadnews.article.mapper.ApCollectionMapper;
import com.leadnews.article.service.ApArticleService;
import com.leadnews.article.service.ApCollectionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leadnews.behaviour.dto.UpdateArticleMess;
import com.leadnews.behaviour.feign.ApBehaviorEntryFeign;
import com.leadnews.common.constants.BC;
import com.leadnews.common.constants.SC;
import com.leadnews.common.exception.LeadNewsException;
import com.leadnews.common.util.RequestContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @description <p>APP收藏信息 业务实现</p>
 *
 * @version 1.0
 * @package com.leadnews.article.service.impl
 */
@Service
public class ApCollectionServiceImpl extends ServiceImpl<ApCollectionMapper, ApCollection> implements ApCollectionService {

    @Autowired
    private ApBehaviorEntryFeign apBehaviorEntryFeign;

    @Autowired
    private ApArticleService apArticleService;


    @Resource
    private KafkaTemplate kafkaTemplate;
    
    /** day10 用户关系行为展示
     * APP收藏行为
     * @param collectionBehaviorDto
     * @return
     * @throws Exception
     */
    @Override
    public void collectionBehavior(CollectionBehaviorDto collectionBehaviorDto) {
        boolean anonymous = RequestContextUtil.isAnonymous();
        Long loginUserId = RequestContextUtil.getUserId();
        Long loginEntryId = null;
        if(anonymous) {
            throw new LeadNewsException("登录用户才能保存");
        } else {
            loginEntryId = apBehaviorEntryFeign.findBehaviorEntryByEntryIdAndType(loginUserId, SC.TYPE_USER).getData().getEntryId();
        }

        if (1 == collectionBehaviorDto.getOperation()) { // 1 这次是取消收藏的行为 前端通过dto传过来的operation行为代表含义，前端接口就是这么写的。
            ApCollection one = Optional.ofNullable(query().eq("entry_id", loginEntryId).eq("article_id", collectionBehaviorDto.getEntryId()).one()).orElseThrow(()->new LeadNewsException("未收藏不能取消"));
            removeById(one.getId());

        } else if ( 0 == collectionBehaviorDto.getOperation()) { // 0 这次是收藏的行为
            ApCollection one = query().eq("entry_id", loginEntryId).eq("article_id", collectionBehaviorDto.getEntryId()).one();
            if(null == one) { // 没查到之前的操作记录，不论是赞还是取消赞
                ApCollection insertPojo = new ApCollection();
                insertPojo.setEntryId(loginEntryId);
                insertPojo.setArticleId(collectionBehaviorDto.getEntryId());
                insertPojo.setType(0);
                insertPojo.setCollectionTime(LocalDateTime.now());
                insertPojo.setPublishedTime(apArticleService.getById(collectionBehaviorDto.getEntryId()).getPublishTime());
                save(insertPojo);

                /* 这里没有更新ApArticle表的收藏数量 */
            }
        } else {
            throw new LeadNewsException("操作有误");
        }


        /*---------------------day12热点文章流数据------------------------------*/
        // 收藏行为，要发消息给kafka流处理器，由流处理器进行聚合统计，再发消息给消费者进行更新，以及重算推荐分数
        UpdateArticleMess updateArticleMess = new UpdateArticleMess();
        updateArticleMess.setArticleId(collectionBehaviorDto.getEntryId());
        updateArticleMess.setType(UpdateArticleMess.UpdateArticleType.COLLECTION);

        updateArticleMess.setNum(collectionBehaviorDto.getOperation() == 0? 1 : -1);

        kafkaTemplate.send(BC.MqConstants.HOT_ARTICLE_SCORE_TOPIC, JSON.toJSONString(updateArticleMess));
    }
}
