package com.leadnews.comment.dto;

import lombok.Data;

import java.util.Date;

/**
 * @version 1.0
 * @description 说明
 * @package com.leadnews.comment.dto
 */
@Data
public class CommentRelyDto {

    /**
     * 评论的ID
     */
    private String commentId;

    // 传递最后一条回复的时间
    private Date minDate;
}
