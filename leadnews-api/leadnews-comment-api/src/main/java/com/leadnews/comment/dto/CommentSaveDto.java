package com.leadnews.comment.dto;

import lombok.Data;

/**
 * @version 1.0
 * @description 说明
 * @package com.leadnews.comment.dto
 */
@Data
public class CommentSaveDto {
    /**
     * 文章id
     */
    private Long articleId;

    /**
     * 评论内容
     */
    private String content;
}
