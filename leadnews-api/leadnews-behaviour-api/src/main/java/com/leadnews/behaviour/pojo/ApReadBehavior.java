package com.leadnews.behaviour.pojo;

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
 * @description <p>APP阅读行为 </p>
 *
 * @version 1.0
 * @package com.leadnews.behaviour.pojo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ap_read_behavior")
@ApiModel(value="ApReadBehavior", description="APP阅读行为")
public class ApReadBehavior implements Serializable {

    // 设置主键采用雪花算法
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(notes = "用户ID", dataType="Long")
    @TableField("entry_id")
    private Long entryId;

    @ApiModelProperty(notes = "文章ID", dataType="Long")
    @TableField("article_id")
    private Long articleId;

    @TableField("count")
    private Integer count;

    @ApiModelProperty(notes = "阅读时间单位秒", dataType="Integer")
    @TableField("read_duration")
    private Integer readDuration;

    @ApiModelProperty(notes = "阅读百分比", dataType="Integer")
    @TableField("percentage")
    private Integer percentage;

    @ApiModelProperty(notes = "文章加载时间", dataType="Integer")
    @TableField("load_duration")
    private Integer loadDuration;

    @ApiModelProperty(notes = "登录时间", dataType="LocalDateTime")
    @TableField("created_time")
    private LocalDateTime createdTime;

    @TableField("updated_time")
    private LocalDateTime updatedTime;


}
