package com.leadnews.behaviour.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leadnews.behaviour.dto.UnLikesBehaviorDto;
import com.leadnews.behaviour.mapper.ApBehaviorEntryMapper;
import com.leadnews.behaviour.mapper.ApUnlikesBehaviorMapper;
import com.leadnews.behaviour.pojo.ApBehaviorEntry;
import com.leadnews.behaviour.pojo.ApUnlikesBehavior;
import com.leadnews.behaviour.service.ApUnlikesBehaviorService;
import com.leadnews.common.constants.SC;
import com.leadnews.common.enums.HttpCodeEnum;
import com.leadnews.common.exception.LeadNewsException;
import com.leadnews.common.util.RequestContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @description <p>APP不喜欢行为 业务实现</p>
 *
 * @version 1.0
 * @package com.leadnews.behaviour.service.impl
 */
@Service
public class ApUnlikesBehaviorServiceImpl extends ServiceImpl<ApUnlikesBehaviorMapper, ApUnlikesBehavior> implements ApUnlikesBehaviorService {

    @Autowired
    private ApBehaviorEntryMapper apBehaviorEntryMapper;
    /**
     * 不喜欢行为
     * @param unLikesBehaviorDto
     */
    @Override
    public void unlikeBehavior(UnLikesBehaviorDto unLikesBehaviorDto) {
        //1. 参数校验
        if(unLikesBehaviorDto == null
                || unLikesBehaviorDto.getArticleId() == null
                || unLikesBehaviorDto.getType() > 2
                || unLikesBehaviorDto.getType() < 0){
            throw new LeadNewsException(HttpCodeEnum.PARAM_INVALID);
        }
        //2. 判断操作实体是匿名还是登录用户
        Long loginUserId = RequestContextUtil.getUserId();
        // 假设实体为当前登录用户id
        Long entryId = loginUserId;
        // 假设实体类型为用户。behaviorEntry中也有type，代表设备还是用户。unlikedto中也有type，0代表不喜欢1代表喜欢
        int type = SC.TYPE_USER;
        if(SC.ANONYMOUS_USER_ID == loginUserId){
            //如果是匿名，则实体类型为备
            type = SC.TYPE_E;
            //那么相应的，实体Id就为设备的id
            entryId = unLikesBehaviorDto.getEquipmentId().longValue();
        }
        //先找人，人存在再找行为
        ApBehaviorEntry entry = apBehaviorEntryMapper.findByEntryIdAndType(entryId, type);
        if(null != entry) {
            //判断记录是否存在，如果存在
            LambdaQueryWrapper<ApUnlikesBehavior> lqw = new LambdaQueryWrapper<>();
            lqw.eq(ApUnlikesBehavior::getArticleId, unLikesBehaviorDto.getArticleId())
                    .eq(ApUnlikesBehavior::getEntryId, entry.getId());
            ApUnlikesBehavior unlikesBehavior = getOne(lqw);
            // 人存在的情况下，再找针对目标的行为。之前有操作行为则更新，没有则添加
            if(null == unlikesBehavior){
                // 不存在则添加
                ApUnlikesBehavior pojo = new ApUnlikesBehavior();
                pojo.setArticleId(unLikesBehaviorDto.getArticleId());
                pojo.setEntryId(entry.getId());
                pojo.setType(unLikesBehaviorDto.getType());// 0是不喜欢，1是喜欢
                pojo.setCreatedTime(LocalDateTime.now());
                save(pojo);
            }else{
                //获取已存在的操作类型
                Integer unlikesBehaviorType = unlikesBehavior.getType();
                if(unlikesBehaviorType.intValue() != unLikesBehaviorDto.getType().intValue()){
                    //如果操作类型不同，则更新
                    ApUnlikesBehavior updatePojo = new ApUnlikesBehavior();
                    updatePojo.setType(unLikesBehaviorDto.getType());
                    updatePojo.setId(unlikesBehavior.getId());
                    updateById(updatePojo);
                }
            }
        }
    }

    /**
     * 远程调用 通过文章Id与行为实体Id 查询是否不喜欢
     * @param articleId
     * @param entryId
     * @return
     */
    @Override
    public ApUnlikesBehavior findByArticleIdAndEntryId(Long articleId, Integer entryId) {
        LambdaQueryWrapper<ApUnlikesBehavior> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ApUnlikesBehavior::getArticleId, articleId)
                .eq(ApUnlikesBehavior::getEntryId, entryId);
        return getOne(lqw);
    }
}
