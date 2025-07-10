package com.leadnews.wemedia.pojo;

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
 * @description <p>自媒体图文内容信息 </p>
 *
 * @version 1.0
 * @package com.leadnews.wemedia.pojo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("wm_news")
@ApiModel(value="WmNews", description="自媒体图文内容信息")
public class WmNews implements Serializable {


    @ApiModelProperty(notes = "主键", dataType="Long")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(notes = "自媒体用户ID", dataType="Long")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty(notes = "标题", dataType="String")
    @TableField("title")
    private String title;

    @ApiModelProperty(notes = "图文内容", dataType="String")
    @TableField("content")
    private String content;

    @ApiModelProperty(notes = "文章布局 0:无图文章 1:单图文章 3:多图文章", dataType="Integer")
    @TableField("type")
    private Integer type;

    @ApiModelProperty(notes = "图文频道ID", dataType="Integer")
    @TableField("channel_id")
    private Integer channelId;

    @TableField("labels")
    private String labels;

    @ApiModelProperty(notes = "创建时间", dataType="LocalDateTime")
    @TableField("created_time")
    private LocalDateTime createdTime;

    @ApiModelProperty(notes = "提交时间", dataType="LocalDateTime")
    @TableField("submited_time")
    private LocalDateTime submitedTime;

    @ApiModelProperty(notes = "当前状态 0:草稿 1:提交（待审核） 2:审核失败 3:人工审核 4:人工审核通过 8:审核通过（待发布） 9:已发布", dataType="Integer")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(notes = "定时发布时间，不定时则为空", dataType="LocalDateTime")
    @TableField("publish_time")
    private LocalDateTime publishTime;

    @ApiModelProperty(notes = "拒绝理由", dataType="String")
    @TableField("reason")
    private String reason;

    @ApiModelProperty(notes = "发布库文章ID", dataType="Long")
    @TableField("article_id")
    private Long articleId;

    @ApiModelProperty(notes = "//图片用逗号分隔", dataType="String")
    @TableField("images")
    private String images;

    @TableField("enable")
    private Integer enable;


}
