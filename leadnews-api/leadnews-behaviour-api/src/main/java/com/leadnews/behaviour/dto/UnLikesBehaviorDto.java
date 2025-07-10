package com.leadnews.behaviour.dto;

import lombok.Data;

/**
 * @version 1.0
 * @description 说明
 * @package com.leadnews.behaviour.dto
 */
@Data
public class UnLikesBehaviorDto {
    // 设备ID
    Integer equipmentId;

    // 文章、动态、评论等ID
    Long articleId;

    /**
     * 不喜欢操作方式
     * 0 不喜欢
     * 1 取消不喜欢
     */
    Integer type;
}
