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
import java.time.LocalDate;

/**
 * @description <p>自媒体图文数据统计 </p>
 *
 * @version 1.0
 * @package com.leadnews.wemedia.pojo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("wm_news_statistics")
@ApiModel(value="WmNewsStatistics", description="自媒体图文数据统计")
public class WmNewsStatistics implements Serializable {


    @ApiModelProperty(notes = "主键", dataType="Long")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(notes = "主账号ID", dataType="Long")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty(notes = "子账号ID", dataType="Long")
    @TableField("article")
    private Long article;

    @ApiModelProperty(notes = "阅读量", dataType="Integer")
    @TableField("read_count")
    private Integer readCount;

    @ApiModelProperty(notes = "评论量", dataType="Integer")
    @TableField("comment")
    private Integer comment;

    @ApiModelProperty(notes = "关注量", dataType="Integer")
    @TableField("follow")
    private Integer follow;

    @ApiModelProperty(notes = "收藏量", dataType="Integer")
    @TableField("collection")
    private Integer collection;

    @ApiModelProperty(notes = "转发量", dataType="Integer")
    @TableField("forward")
    private Integer forward;

    @ApiModelProperty(notes = "点赞量", dataType="Integer")
    @TableField("likes")
    private Integer likes;

    @ApiModelProperty(notes = "不喜欢", dataType="Integer")
    @TableField("unlikes")
    private Integer unlikes;

    @ApiModelProperty(notes = "取消关注量", dataType="Integer")
    @TableField("unfollow")
    private Integer unfollow;

    @TableField("burst")
    private String burst;

    @ApiModelProperty(notes = "创建时间", dataType="LocalDate")
    @TableField("created_time")
    private LocalDate createdTime;


}
