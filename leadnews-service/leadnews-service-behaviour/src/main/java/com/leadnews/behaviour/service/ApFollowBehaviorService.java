package com.leadnews.behaviour.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leadnews.behaviour.dto.FollowBehaviourDto;
import com.leadnews.behaviour.pojo.ApFollowBehavior;

/**
 * @description <p>APP关注行为 业务接口</p>
 *
 * @version 1.0
 * @package com.leadnews.behaviour.service
 */
public interface ApFollowBehaviorService extends IService<ApFollowBehavior> {

    /**
     * @description <p>APP关注行为 业务接口</p>
     *
     * @version 1.0
     * @package com.leadnews.behaviour.service
     */
    void saveFollowBehaviour(FollowBehaviourDto dto);
}
