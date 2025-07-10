package com.leadnews.comment.dto;

import lombok.Data;

/**
 * @version 1.0
 * @description 说明
 * @package com.leadnews.comment.dto
 */
@Data
public class CommentLikeDto {
    /**
     * 评论id
     */
    private String commentId;

    /** 前端传过来的命令是0是点赞！
     * 1：点赞
     * 0：取消点赞
     */
    private Integer operation;
}