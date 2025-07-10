package com.leadnews.user.dto;

import lombok.Data;

/**
 * @version 1.0
 * @description 说明
 * @package com.leadnews.user.dto
 */
@Data
public class UserRelationDto {
    // 文章作者ID
    private Long authorId;

    // 文章id
    private Long articleId;
    /**
     * 操作方式
     * 0  关注
     * 1  取消
     */
    private Integer operation;
}
