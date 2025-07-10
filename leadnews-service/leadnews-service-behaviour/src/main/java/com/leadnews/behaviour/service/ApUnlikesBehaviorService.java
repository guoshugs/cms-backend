package com.leadnews.behaviour.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leadnews.behaviour.dto.UnLikesBehaviorDto;
import com.leadnews.behaviour.pojo.ApUnlikesBehavior;

/**
 * @description <p>APP不喜欢行为 业务接口</p>
 *
 * @version 1.0
 * @package com.leadnews.behaviour.service
 */
public interface ApUnlikesBehaviorService extends IService<ApUnlikesBehavior> {

    /**
     * 不喜欢行为
     * @param unLikesBehaviorDto
     */
    void unlikeBehavior(UnLikesBehaviorDto unLikesBehaviorDto);

    /**
     * 远程调用 通过文章Id与行为实体Id 查询是否不喜欢
     * @param articleId
     * @param entryId
     * @return
     */
    ApUnlikesBehavior findByArticleIdAndEntryId(Long articleId, Integer entryId);
}
