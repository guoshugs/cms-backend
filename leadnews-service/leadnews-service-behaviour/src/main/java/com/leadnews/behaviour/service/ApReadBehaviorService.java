package com.leadnews.behaviour.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leadnews.behaviour.dto.ReadBehaviorDto;
import com.leadnews.behaviour.pojo.ApReadBehavior;

/**
 * @description <p>APP阅读行为 业务接口</p>
 *
 * @version 1.0
 * @package com.leadnews.behaviour.service
 */
public interface ApReadBehaviorService extends IService<ApReadBehavior> {
    /**
     * 收集用户阅读行为
     * @param dto
     */
    void saveReadBehavior(ReadBehaviorDto dto);
}
