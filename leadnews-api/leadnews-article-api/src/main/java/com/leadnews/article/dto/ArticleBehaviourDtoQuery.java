package com.leadnews.article.dto;

import lombok.Data;

/**
 * @version 1.0
 * @description 说明
 * @package com.leadnews.article.dto
 */
@Data
public class ArticleBehaviourDtoQuery {
    // 设备ID
    private Integer equipmentId;
    // 文章ID
    private Long articleId;
    // 作者ID
    private Integer authorId;
}