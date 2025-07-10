package com.leadnews.comment.dto;

import lombok.Data;

import java.util.Date;

/**
 * @version 1.0
 * @description 说明
 * @package com.leadnews.comment.dto
 */
@Data
public class CommentDto {

    /**
     * 文章id
     */
    private Long articleId;

    //每次分页后的最小时间
    private Date minDate;
}
