package com.leadnews.behaviour.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leadnews.behaviour.dto.FollowBehaviourDto;
import com.leadnews.behaviour.mapper.ApBehaviorEntryMapper;
import com.leadnews.behaviour.mapper.ApFollowBehaviorMapper;
import com.leadnews.behaviour.pojo.ApBehaviorEntry;
import com.leadnews.behaviour.pojo.ApFollowBehavior;
import com.leadnews.behaviour.service.ApFollowBehaviorService;
import com.leadnews.common.constants.SC;
import com.leadnews.common.exception.LeadNewsException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @description <p>APP关注行为 业务实现</p>
 *
 * @version 1.0
 * @package com.leadnews.behaviour.service.impl
 */
@Service
public class ApFollowBehaviorServiceImpl extends ServiceImpl<ApFollowBehaviorMapper, ApFollowBehavior> implements ApFollowBehaviorService {
    @Resource
    private ApBehaviorEntryMapper apBehaviorEntryMapper;

    /**
     * @description <p>APP关注行为 业务接口</p>
     *
     * @version 1.0
     * @package com.leadnews.behaviour.service
     */
    @Override
    public void saveFollowBehaviour(FollowBehaviourDto dto) {
        Long articleId = dto.getArticleId();
        Integer equipmentId = dto.getEquipmentId(); // 并没有用前端传过来的设备id，因为必须注册才能关注，所以必须从entry表中去查用户id，一个用户多台设备不能每台都关注一回。所以不能用设备id
        Long followId = dto.getFollowId();
        Long userId = dto.getUserId();

        // follow_behaviour表中的entry_id是behaviour_entry表中的id
        // behaviour_entry表中的才是传过来的userId，才是实体id

        // 查询是否有该实体
        LambdaQueryWrapper<ApBehaviorEntry> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ApBehaviorEntry::getEntryId, userId).eq(ApBehaviorEntry::getType, SC.TYPE_USER);
        ApBehaviorEntry entry = Optional.ofNullable(apBehaviorEntryMapper.selectOne(lqw)).orElseThrow(() -> new LeadNewsException("该实体没有行为记录"));

        //1. 查询出实体id
        //ApBehaviorEntry entry = apBehaviorEntryMapper.findByEntryIdAndType(userId, SC.TYPE_USER); // 这是用MyBatis在mapper曾写sql的方法。上面是MyBatisPlus用QueryWrapper的方法。

        // 查询该实体是否已经被添加该行为了，为了幂等性，不然会一直添加。另外，article_id其实和follow_id得到的关注行为是一样的，选一个条件就可以了
        ApFollowBehavior behavior = query().eq("entry_id", entry.getId()).eq("follow_id", followId).one();  // 用户和创作者之间只有一条有向边
        if(null == behavior){
            ApFollowBehavior apFollowBehavior = new ApFollowBehavior();
            apFollowBehavior.setEntryId(entry.getId());
            apFollowBehavior.setArticleId(articleId);
            apFollowBehavior.setFollowId(followId);
            apFollowBehavior.setCreatedTime(LocalDateTime.now());

            save(apFollowBehavior);
        }
        //4. 有则不处理
    }
}
