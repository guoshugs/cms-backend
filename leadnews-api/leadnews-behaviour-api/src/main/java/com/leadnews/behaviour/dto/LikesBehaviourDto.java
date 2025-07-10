package com.leadnews.behaviour.dto;

import lombok.Data;

/**
 * @version 1.0
 * @description 说明
 * @package com.leadnews.behaviour.dto
 */
@Data
public class LikesBehaviourDto {
    // 设备ID
    private Integer equipmentId;

    // 文章、动态、评论等ID
    private Long articleId;
    
    /**
     * 喜欢内容类型
     * 0文章
     * 1动态
     * 2评论
     */
    private Integer type;

    /**
     * 喜欢操作方式
     * 1 点赞
     * 0 取消点赞
     */
    private Integer operation;
}