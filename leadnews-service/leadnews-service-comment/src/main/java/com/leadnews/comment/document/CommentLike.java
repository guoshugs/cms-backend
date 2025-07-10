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
@Document("comment_like")
public class CommentLike {

    /**
     * id
     */
    @Id
    private String id;

    /**
     * 点赞人的ID
     */
    private Integer userId;

    /**
     * 被点赞的评论id
     */
    private String commentId;


}