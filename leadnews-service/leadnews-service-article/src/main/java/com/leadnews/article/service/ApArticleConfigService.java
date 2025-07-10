package com.leadnews.article.service;

import com.leadnews.article.pojo.ApArticleConfig;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @description <p>APP已发布文章配置 业务接口</p>
 *
 * @version 1.0
 * @package com.leadnews.article.service
 */
public interface ApArticleConfigService extends IService<ApArticleConfig> {
    /**
     * 同步文章的上下架
     * @param message
     */
    void upOrDown(String message);
}
