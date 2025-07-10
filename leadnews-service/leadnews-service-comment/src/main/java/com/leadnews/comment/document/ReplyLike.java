package com.leadnews.comment.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @version 1.0
 * @description 说明
 * @package com.leadnews.comment.document
 */
@Data
@Document("ap_comment_reply_like")
public class ReplyLike {

    /**
     * id
     */
    @Id
    private String id;

    /**
     * 点赞了回复的用户ID
     */
    private Integer userId;

    /**
     * 点赞了哪一条回复
     */
    private String replyId;


    //点赞 就是有记录 取消点赞 就是删除记录即可

   /* *//**
     * 1：点赞
     * 0：取消点赞
     *//*
    private Integer operation;*/
}