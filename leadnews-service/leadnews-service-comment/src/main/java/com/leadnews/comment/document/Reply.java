package com.leadnews.comment.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * @version 1.0
 * @description 说明
 * @package com.leadnews.comment.document
 */
@Data
@Document("ap_comment_reply")
public class Reply {
    /**
     * id
     */
    @Id
    private String id;

    /**
     * 写下回复的 用户的ID
     */
    private Integer userId;

    /**
     * 写下回复的 用户的昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String headImage;

    /**
     * 针对的是哪条 评论id 进行回复
     */
    private String commentId;

    /**
     * 回复的内容
     */
    private String content;

    /**
     * 点赞数（回复本身的点赞数量）
     */
    private Integer likes;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;

}