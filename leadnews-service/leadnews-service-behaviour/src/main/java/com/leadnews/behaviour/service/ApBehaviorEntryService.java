package com.leadnews.behaviour.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leadnews.behaviour.pojo.ApBehaviorEntry;

/**
 * @description <p>APP行为实体,一个行为实体可能是用户或者设备，或者其它 业务接口</p>
 *
 * @version 1.0
 * @package com.leadnews.behaviour.service
 */
public interface ApBehaviorEntryService extends IService<ApBehaviorEntry> {

    /**
     * 远程调用 通过设备或用户id与类型，查询行为id
     * @param userId
     * @param type
     * @return
     */
    ApBehaviorEntry findByEntryIdAndType(Long userId, Integer type);
}
