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
 * @description <p>自媒体粉丝数据统计 </p>
 *
 * @version 1.0
 * @package com.leadnews.wemedia.pojo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("wm_fans_statistics")
@ApiModel(value="WmFansStatistics", description="自媒体粉丝数据统计")
public class WmFansStatistics implements Serializable {


    @ApiModelProperty(notes = "主键", dataType="Long")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(notes = "主账号ID", dataType="Long")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty(notes = "子账号ID", dataType="Long")
    @TableField("article")
    private Long article;

    @TableField("read_count")
    private Integer readCount;

    @TableField("comment")
    private Integer comment;

    @TableField("follow")
    private Integer follow;

    @TableField("collection")
    private Integer collection;

    @TableField("forward")
    private Integer forward;

    @TableField("likes")
    private Integer likes;

    @TableField("unlikes")
    private Integer unlikes;

    @TableField("unfollow")
    private Integer unfollow;

    @TableField("burst")
    private String burst;

    @ApiModelProperty(notes = "创建时间", dataType="LocalDate")
    @TableField("created_time")
    private LocalDate createdTime;


}
