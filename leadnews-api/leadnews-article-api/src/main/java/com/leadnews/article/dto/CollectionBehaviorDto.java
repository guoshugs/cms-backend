package com.leadnews.article.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @version 1.0
 * @description 说明
 * @package com.leadnews.article.dto
 */
@Data
public class CollectionBehaviorDto {
    // 设备ID
    private Integer equipmentId;

    // 文章、动态ID
    private Long entryId;

    /**
     * 收藏内容类型
     * 0文章
     * 1动态
     */
    private Integer type;

    /**
     * 操作类型
     * 0收藏
     * 1取消收藏
     */
    private Integer operation;

    /**
     发布时间 冗余字段 接收页面文章的发布时间值
     */
    private LocalDateTime publishedTime;
}
