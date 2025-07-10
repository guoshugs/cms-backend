package com.leadnews.article.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.leadnews.common.util.Long2StringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description <p>已发布的文章信息 </p>
 *
 * @version 1.0
 * @package com.leadnews.article.pojo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ap_article")
@ApiModel(value="ApArticle", description="已发布的文章信息")
public class ApArticle implements Serializable {


    @TableId(value = "id", type = IdType.ID_WORKER)
    @JsonSerialize(using = Long2StringSerializer.class)
    private Long id;

    @ApiModelProperty(notes = "标题", dataType="String")
    @TableField("title")
    private String title;

    @ApiModelProperty(notes = "文章作者的ID", dataType="Long")
    @TableField("author_id")
    private Long authorId;

    @ApiModelProperty(notes = "作者昵称", dataType="String")
    @TableField("author_name")
    private String authorName;

    @ApiModelProperty(notes = "文章所属频道ID", dataType="Integer")
    @TableField("channel_id")
    private Integer channelId;

    @ApiModelProperty(notes = "频道名称", dataType="String")
    @TableField("channel_name")
    private String channelName;

    @ApiModelProperty(notes = "文章布局 0:无图文章 1:单图文章 2:多图文章", dataType="Integer")
    @TableField("layout")
    private Integer layout;

    @ApiModelProperty(notes = "文章标记 0:普通文章 1:热点文章 2:置顶文章 3:精品文章 4:大V文章", dataType="Integer")
    @TableField("flag")
    private Integer flag;

    @ApiModelProperty(notes = "文章图片,多张逗号分隔", dataType="String")
    @TableField("images")
    private String images;

    @ApiModelProperty(notes = "文章标签最多3个,逗号分隔", dataType="String")
    @TableField("labels")
    private String labels;

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

    @ApiModelProperty(notes = "省市", dataType="Integer")
    @TableField("province_id")
    private Integer provinceId;

    @ApiModelProperty(notes = "市区", dataType="Integer")
    @TableField("city_id")
    private Integer cityId;

    @ApiModelProperty(notes = "区县", dataType="Integer")
    @TableField("county_id")
    private Integer countyId;

    @ApiModelProperty(notes = "创建时间", dataType="LocalDateTime")
    @TableField("created_time")
    private LocalDateTime createdTime;

    @ApiModelProperty(notes = "发布时间", dataType="LocalDateTime")
    @TableField("publish_time")
    private LocalDateTime publishTime;

    @ApiModelProperty(notes = "同步状态", dataType="Integer")
    @TableField("sync_status")
    private Integer syncStatus;

    @ApiModelProperty(notes = "来源", dataType="Integer")
    @TableField("origin")
    private Integer origin;


}
