package com.leadnews.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.leadnews.user.pojo.ApUserFollow;
import com.leadnews.user.mapper.ApUserFollowMapper;
import com.leadnews.user.service.ApUserFollowService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @description <p>APP用户关注信息 业务实现</p>
 *
 * @version 1.0
 * @package com.leadnews.user.service.impl
 */
@Service
public class ApUserFollowServiceImpl extends ServiceImpl<ApUserFollowMapper, ApUserFollow> implements ApUserFollowService {

    /** day10 用户关系行为展示
     * 查询用户是否关注了作者
     * @param userId
     * @param followId
     * @return
     */
    @Override
    public ApUserFollow findUserFollowByUserIdFollowId(Integer userId, Integer followId) {
        LambdaQueryWrapper<ApUserFollow> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ApUserFollow::getUserId,userId)
                .eq(ApUserFollow::getFollowId, followId);
        return getOne(lqw);
    }
}
