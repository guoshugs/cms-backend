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
 * @description <p>APP点赞行为 </p>
 *
 * @version 1.0
 * @package com.leadnews.behaviour.pojo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ap_likes_behavior")
@ApiModel(value="ApLikesBehavior", description="APP点赞行为")
public class ApLikesBehavior implements Serializable {

    // 点赞 主键没有自增，我们使用雪花算法来生成主键。1. 设置类型为ID_WORKER 2.配置文件
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(notes = "实体ID", dataType="Long")
    @TableField("entry_id")
    private Long entryId;

    @ApiModelProperty(notes = "文章ID", dataType="Long")
    @TableField("article_id")
    private Long articleId;

    @ApiModelProperty(notes = "点赞内容类型 0:文章 1:动态", dataType="Integer")
    @TableField("type")
    private Integer type;

    @ApiModelProperty(notes = "0:点赞 1:取消点赞", dataType="Integer")
    @TableField("operation")
    private Integer operation;

    @ApiModelProperty(notes = "登录时间", dataType="LocalDateTime")
    @TableField("created_time")
    private LocalDateTime createdTime;


}
