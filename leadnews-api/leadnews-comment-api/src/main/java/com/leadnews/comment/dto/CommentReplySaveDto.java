package com.leadnews.comment.dto;

import lombok.Data;

/**
 * @version 1.0
 * @description 说明
 * @package com.leadnews.comment.dto
 */
@Data
public class CommentReplySaveDto {

    //回复内容
    private String content;

    //针对哪一个评论进行回复
    private String commentId;
}
