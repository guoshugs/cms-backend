package com.leadnews.comment.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * @version 1.0
 * @description 说明 mongodb里面的表，不叫pojo，叫document
 * @package com.leadnews.comment.document
 */
/* 如果要实现对评论再评论的功能，那么comment document的属性还要增加parentId*/
@Data
@Document("ap_comment")//定义集合（表）的名称
public class Comment {

    /**
     * id 主键  ObjectId
     */
    @Id
    private String id;
    // 如果需要多级评论，它是哪个评论下的评论内容
    // 1: abc ,  2,parentId=1, 2属于在1的下方再评论
    //private String parentId;
    /* 如果parentId不为空，说明它是子评论。如果parentId为空，说明它是一级评论。*/

    /**
     * 评论人的ID
     */
    private Long userId;

    /**
     * 评论人的昵称
     */
    private String nickName;

    /**
     * 评论人的头像
     */
    private String headImage;

    /**
     * 文章id
     */
    private Long articleId;

    /**
     * 评论人写的内容
     */
    private String content;

    /**
     * 总的点赞数
     */
    private Integer likes;

    /**
     * 总的回复数
     */
    private Integer replies;

    /**
     * 评论时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;

}