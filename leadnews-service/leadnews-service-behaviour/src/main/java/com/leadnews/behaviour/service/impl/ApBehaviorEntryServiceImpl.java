package com.leadnews.behaviour.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leadnews.behaviour.mapper.ApBehaviorEntryMapper;
import com.leadnews.behaviour.pojo.ApBehaviorEntry;
import com.leadnews.behaviour.service.ApBehaviorEntryService;
import org.springframework.stereotype.Service;

/**
 * @description <p>APP行为实体,一个行为实体可能是用户或者设备，或者其它 业务实现</p>
 *
 * @version 1.0
 * @package com.leadnews.behaviour.service.impl
 */
@Service
public class ApBehaviorEntryServiceImpl extends ServiceImpl<ApBehaviorEntryMapper, ApBehaviorEntry> implements ApBehaviorEntryService {

    /**
     * 远程调用 通过设备或用户id与类型，查询行为id
     *
     * @param userId
     * @param type
     * @return
     */
    @Override
    public ApBehaviorEntry findByEntryIdAndType(Long userId, Integer type) {
        return getBaseMapper().findByEntryIdAndType(userId,type);
    }
}
