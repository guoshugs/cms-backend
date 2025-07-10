package com.leadnews.comment.dto;

import lombok.Data;

/**
 * @version 1.0
 * @description 说明
 * @package com.leadnews.comment.dto
 */
@Data
public class CommentReplyLikeDto {

    /**
     * 针对 回复的ID  为该回复进行点赞或者取消点赞
     */
    private String commentRepayId;

    /**
     * 1：点赞
     * 0：取消点赞
     */
    private Integer operation;
}
