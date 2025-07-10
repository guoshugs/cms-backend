package com.leadnews.behaviour.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leadnews.behaviour.dto.LikesBehaviourDto;
import com.leadnews.behaviour.dto.UpdateArticleMess;
import com.leadnews.behaviour.mapper.ApBehaviorEntryMapper;
import com.leadnews.behaviour.mapper.ApLikesBehaviorMapper;
import com.leadnews.behaviour.pojo.ApLikesBehavior;
import com.leadnews.behaviour.service.ApLikesBehaviorService;
import com.leadnews.common.constants.BC;
import com.leadnews.common.constants.SC;
import com.leadnews.common.exception.LeadNewsException;
import com.leadnews.common.util.RequestContextUtil;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @description <p>APP点赞行为 业务实现</p>
 *
 * @version 1.0
 * @package com.leadnews.behaviour.service.impl
 */
@Service
public class ApLikesBehaviorServiceImpl extends ServiceImpl<ApLikesBehaviorMapper, ApLikesBehavior> implements ApLikesBehaviorService {
    @Resource
    private ApBehaviorEntryMapper apBehaviorEntryMapper;

    @Resource
    private KafkaTemplate kafkaTemplate;


    /**
     * APP点赞与取消
     * @param dto
     * 赞只是用来做推荐推广的，赞越多，排名越高，并不需要用户登录才能点赞。没登陆的，关联设备id，登录过的，关联用户id
     */
    @Override
    public void likeBehavior(LikesBehaviourDto dto) {
        boolean anonymous = RequestContextUtil.isAnonymous();
        Long userId = RequestContextUtil.getUserId();
        Long entryId = null;
        if(anonymous) {
            entryId = apBehaviorEntryMapper.findByEntryIdAndType(dto.getEquipmentId().longValue(), SC.TYPE_E).getId();
        } else {
            entryId = apBehaviorEntryMapper.findByEntryIdAndType(userId, SC.TYPE_USER).getId();
        }

        if (1 == dto.getOperation()) { // 1 这次是取消点赞的行为 前端通过dto传过来的operation行为代表含义，前端接口就是这么写的。
            ApLikesBehavior one = Optional.ofNullable(query().eq("entry_id", entryId).eq("article_id", dto.getArticleId()).one()).orElseThrow(()->new LeadNewsException("未点赞不能取消"));
            ApLikesBehavior updatePojo = new ApLikesBehavior();
            updatePojo.setOperation(1); // 该表的operation代表含义：0:点赞 1:取消点赞
            updatePojo.setId(one.getId());
            updateById(updatePojo);

        } else if ( 0 == dto.getOperation()) { // 0 这次是点赞的行为
            ApLikesBehavior one = query().eq("entry_id", entryId).eq("article_id", dto.getArticleId()).one();
            if(null == one) { // 没查到之前的操作记录，不论是赞还是取消赞
                ApLikesBehavior insertPojo = new ApLikesBehavior();
                insertPojo.setEntryId(entryId);
                insertPojo.setArticleId(dto.getArticleId());
                insertPojo.setType(0);
                insertPojo.setOperation(0);// 该表的operation代表含义：0:点赞 1:取消点赞
                insertPojo.setCreatedTime(LocalDateTime.now());
                save(insertPojo);
            } else { // 查到之前的操作记录，判断之前的行为是赞还是取消赞
                if (one.getOperation() == 0) {
                    throw  new LeadNewsException("已经点过赞了");
                } else if (one.getOperation() == 1){
                    ApLikesBehavior updatePojo = new ApLikesBehavior();
                    updatePojo.setOperation(0); // 该表的operation代表含义：0:点赞 1:取消点赞
                    updatePojo.setId(one.getId());
                    updateById(updatePojo);
                }
            }
        } else {
            throw new LeadNewsException("操作有误");
        }

        /*---------------------day12热点文章流数据------------------------------*/
        // 点赞or取消行为，要发消息给kafka流处理器，由流处理器进行聚合统计，再发消息给消费者进行更新，以及重算推荐分数
        UpdateArticleMess updateArticleMess = new UpdateArticleMess();
        updateArticleMess.setArticleId(dto.getArticleId());
        updateArticleMess.setType(UpdateArticleMess.UpdateArticleType.LIKES);

        updateArticleMess.setNum(dto.getOperation() == 0? 1 : -1);

        kafkaTemplate.send(BC.MqConstants.HOT_ARTICLE_SCORE_TOPIC, JSON.toJSONString(updateArticleMess));
    }


    /**
     * 远程调用 通过文章Id与行为实体Id 查询是否点赞
     * @param articleId
     * @param entryId
     * @return
     */
    @Override
    public ApLikesBehavior findByArticleIdAndEntryId(Long articleId, Integer entryId) {
        LambdaQueryWrapper<ApLikesBehavior> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ApLikesBehavior::getArticleId, articleId)
                .eq(ApLikesBehavior::getEntryId, entryId);
        return getOne(lqw);
    }
}
