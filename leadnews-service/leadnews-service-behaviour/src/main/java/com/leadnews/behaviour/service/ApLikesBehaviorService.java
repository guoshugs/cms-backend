package com.leadnews.behaviour.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leadnews.behaviour.dto.LikesBehaviourDto;
import com.leadnews.behaviour.pojo.ApLikesBehavior;

/**
 * @description <p>APP点赞行为 业务接口</p>
 *
 * @version 1.0
 * @package com.leadnews.behaviour.service
 */
public interface ApLikesBehaviorService extends IService<ApLikesBehavior> {
    /**
     * APP点赞行为
     * @param likesBehaviourDto
     * @return
     * @throws Exception
     */
    void likeBehavior(LikesBehaviourDto dto);

    /**
     * 远程调用 通过文章Id与行为实体Id 查询是否点赞
     * @param articleId
     * @param entryId
     * @return
     */
    ApLikesBehavior findByArticleIdAndEntryId(Long articleId, Integer entryId);
}
