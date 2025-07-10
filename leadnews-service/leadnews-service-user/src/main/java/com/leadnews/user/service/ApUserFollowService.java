package com.leadnews.user.service;

import com.leadnews.user.pojo.ApUserFollow;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @description <p>APP用户关注信息 业务接口</p>
 *
 * @version 1.0
 * @package com.leadnews.user.service
 */
public interface ApUserFollowService extends IService<ApUserFollow> {

    /** day10 用户关系行为展示
     * 查询用户是否关注了作者
     * @param userId
     * @param followId
     * @return
     */
    ApUserFollow findUserFollowByUserIdFollowId(Integer userId, Integer followId);
}
