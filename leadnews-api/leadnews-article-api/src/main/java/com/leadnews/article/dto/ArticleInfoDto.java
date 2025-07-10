package com.leadnews.article.dto;

import com.leadnews.article.pojo.ApArticle;
import com.leadnews.article.pojo.ApArticleConfig;
import com.leadnews.article.pojo.ApArticleContent;
import lombok.Data;

/**
 * @version 1.0
 * @description 需要同步的文章信息
 * @package com.leadnews.article.dto
 */
@Data
public class ArticleInfoDto {
    /** 对应文章表数据 */
    private ApArticle apArticle;
    /** 文章内容数据 */
    private ApArticleContent apArticleContent;
    /** 文章配置数据 */
    private ApArticleConfig apArticleConfig;
    /** 自媒体文章id。类似回调，传给article的同时添加一个回调的id：wemedia微服务中的id */
    private Long wmNewsId;

}
