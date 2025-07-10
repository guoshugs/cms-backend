package com.leadnews.behaviour.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leadnews.behaviour.dto.ReadBehaviorDto;
import com.leadnews.behaviour.dto.UpdateArticleMess;
import com.leadnews.behaviour.mapper.ApBehaviorEntryMapper;
import com.leadnews.behaviour.mapper.ApReadBehaviorMapper;
import com.leadnews.behaviour.pojo.ApBehaviorEntry;
import com.leadnews.behaviour.pojo.ApReadBehavior;
import com.leadnews.behaviour.service.ApReadBehaviorService;
import com.leadnews.common.constants.BC;
import com.leadnews.common.constants.SC;
import com.leadnews.common.util.RequestContextUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @description <p>APP阅读行为 业务实现</p>
 *
 * @version 1.0
 * @package com.leadnews.behaviour.service.impl
 */
@Service
public class ApReadBehaviorServiceImpl extends ServiceImpl<ApReadBehaviorMapper, ApReadBehavior> implements ApReadBehaviorService {

    @Autowired
    private ApBehaviorEntryMapper apBehaviorEntryMapper;

    @Resource
    private KafkaTemplate kafkaTemplate;

    /**
     * APP阅读行为
     *
     * @param dto
     */
    @Override
    public void saveReadBehavior(ReadBehaviorDto dto) {
        //1. 参数校验
        //2. 判断操作实体是匿名还是登录用户
        long loginUserId = RequestContextUtil.getUserId();
        // 假设实体为当前登录用户id
        long entryId = loginUserId;
        // 假设实体类型为用户
        int type = SC.TYPE_USER;
        if(SC.ANONYMOUS_USER_ID == loginUserId){
            //如果是匿名，则实体类型为备
            type = SC.TYPE_E;
            //那么相应的，实体Id就为设备的id
            entryId = dto.getEquipmentId().longValue();
        }
        ApBehaviorEntry entry = apBehaviorEntryMapper.findByEntryIdAndType(entryId, type);
        //3.保存或更新阅读的行为
        LambdaQueryWrapper<ApReadBehavior> lqw = new LambdaQueryWrapper<ApReadBehavior>();
        lqw.eq(ApReadBehavior::getEntryId,entry.getEntryId());
        lqw.eq(ApReadBehavior::getArticleId,dto.getArticleId());

        ApReadBehavior apReadBehavior = getOne(lqw);
        ApReadBehavior pojo = new ApReadBehavior();

        if(apReadBehavior == null){
            BeanUtils.copyProperties(dto,pojo);
            pojo.setEntryId(entry.getEntryId());
            pojo.setCreatedTime(LocalDateTime.now());
            save(pojo);
        }else{
            pojo.setId(apReadBehavior.getId());
            //增加阅读时长
            pojo.setReadDuration(apReadBehavior.getReadDuration()+dto.getReadDuration());
            //增加加载时长
            pojo.setLoadDuration(apReadBehavior.getLoadDuration() + dto.getLoadDuration());
            //重置已读百分比
            pojo.setPercentage(dto.getPercentage());
            pojo.setUpdatedTime(LocalDateTime.now());
            //增加阅读次数
            pojo.setCount((apReadBehavior.getCount()+1));
            updateById(pojo);
        }

        /*---------------------day12热点文章流数据------------------------------*/
        // 阅读行为，要发消息给kafka流处理器，由流处理器进行聚合统计，再发消息给消费者进行更新，以及重算推荐分数
        UpdateArticleMess updateArticleMess = new UpdateArticleMess();
        updateArticleMess.setArticleId(dto.getArticleId());
        updateArticleMess.setType(UpdateArticleMess.UpdateArticleType.VIEWS);

        updateArticleMess.setNum(1);
        kafkaTemplate.send(BC.MqConstants.HOT_ARTICLE_SCORE_TOPIC, JSON.toJSONString(updateArticleMess));
    }
}
