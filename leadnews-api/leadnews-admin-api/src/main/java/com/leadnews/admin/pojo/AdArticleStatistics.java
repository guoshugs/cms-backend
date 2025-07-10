package com.leadnews.admin.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description <p>文章数据统计 </p>
 *
 * @version 1.0
 * @package com.leadnews.admin.pojo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ad_article_statistics")
@ApiModel(value="AdArticleStatistics", description="文章数据统计")
public class AdArticleStatistics implements Serializable {


    @ApiModelProperty(notes = "主键", dataType="Long")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(notes = "主账号ID", dataType="Long")
    @TableField("article_we_media")
    private Long articleWeMedia;

    @ApiModelProperty(notes = "子账号ID", dataType="Long")
    @TableField("article_crawlers")
    private Long articleCrawlers;

    @ApiModelProperty(notes = "频道ID", dataType="Integer")
    @TableField("channel_id")
    private Integer channelId;

    @ApiModelProperty(notes = "草读量", dataType="Integer")
    @TableField("read_20")
    private Integer read20;

    @ApiModelProperty(notes = "读完量", dataType="Integer")
    @TableField("read_100")
    private Integer read100;

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

    @ApiModelProperty(notes = "unfollow", dataType="Integer")
    @TableField("unfollow")
    private Integer unfollow;

    @ApiModelProperty(notes = "创建时间", dataType="LocalDateTime")
    @TableField("created_time")
    private LocalDateTime createdTime;


}
