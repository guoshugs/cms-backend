package com.leadnews.article.vo;

import com.leadnews.article.pojo.ApArticleConfig;
import com.leadnews.article.pojo.ApArticleContent;
import lombok.Data;

/**
 * @version 1.0
 * @description 说明
 * @package com.leadnews.article.vo
 */
@Data
public class ArticleInfoVo {
    //文章配置信息
    private ApArticleConfig config;
    //文章内容
    private ApArticleContent content;
}
