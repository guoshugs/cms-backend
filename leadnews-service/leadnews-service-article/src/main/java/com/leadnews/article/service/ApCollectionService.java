package com.leadnews.article.service;

import com.leadnews.article.dto.CollectionBehaviorDto;
import com.leadnews.article.pojo.ApCollection;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @description <p>APP收藏信息 业务接口</p>
 *
 * @version 1.0
 * @package com.leadnews.article.service
 */
public interface ApCollectionService extends IService<ApCollection> {

    /** day10 用户关系行为展示
     * APP收藏行为
     * @param collectionBehaviorDto
     * @return
     * @throws Exception
     */
    void collectionBehavior(CollectionBehaviorDto collectionBehaviorDto);
}
