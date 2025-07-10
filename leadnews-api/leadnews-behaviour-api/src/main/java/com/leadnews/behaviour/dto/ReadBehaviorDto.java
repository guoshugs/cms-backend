package com.leadnews.behaviour.dto;

import lombok.Data;

/**
 * @version 1.0
 * @description 说明
 * @package com.leadnews.behaviour.dto
 */
@Data
public class ReadBehaviorDto {
    // 设备ID
    private Integer equipmentId;

    // 文章、动态、评论等ID
    private Long articleId;

    /**
     * 阅读次数
     */
    private Integer count;

    /**
     * 阅读时长（S)
     */
    private Integer readDuration;

    /**
     * 阅读百分比 不带小数
     */
    private Integer percentage;

    /**
     * 加载时间
     */
    private Integer loadDuration;

}