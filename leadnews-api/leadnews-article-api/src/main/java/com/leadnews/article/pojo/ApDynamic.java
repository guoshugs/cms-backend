package com.leadnews.article.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description <p>APP用户动态信息 </p>
 *
 * @version 1.0
 * @package com.leadnews.article.pojo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ap_dynamic")
@ApiModel(value="ApDynamic", description="APP用户动态信息")
public class ApDynamic implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(notes = "文章作者的ID", dataType="Long")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty(notes = "作者昵称", dataType="String")
    @TableField("user_name")
    private String userName;

    @ApiModelProperty(notes = "频道名称", dataType="String")
    @TableField("content")
    private String content;

    @ApiModelProperty(notes = "是否转发", dataType="Integer")
    @TableField("is_forward")
    private Integer isForward;

    @ApiModelProperty(notes = "转发文章ID", dataType="Long")
    @TableField("article_id")
    private Long articleId;

    @ApiModelProperty(notes = "转发文章标题", dataType="String")
    @TableField("articel_title")
    private String articelTitle;

    @ApiModelProperty(notes = "转发文章图片", dataType="String")
    @TableField("article_image")
    private String articleImage;

    @ApiModelProperty(notes = "布局标识 0:无图动态 1:单图动态 2:多图动态 3:转发动态", dataType="Integer")
    @TableField("layout")
    private Integer layout;

    @ApiModelProperty(notes = "文章图片,多张逗号分隔", dataType="String")
    @TableField("images")
    private String images;

    @ApiModelProperty(notes = "点赞数量", dataType="Integer")
    @TableField("likes")
    private Integer likes;

    @ApiModelProperty(notes = "收藏数量", dataType="Integer")
    @TableField("collection")
    private Integer collection;

    @ApiModelProperty(notes = "评论数量", dataType="Integer")
    @TableField("comment")
    private Integer comment;

    @ApiModelProperty(notes = "阅读数量", dataType="Integer")
    @TableField("views")
    private Integer views;

    @ApiModelProperty(notes = "创建时间", dataType="LocalDateTime")
    @TableField("created_time")
    private LocalDateTime createdTime;


}
