package com.leadnews.user.pojo;

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
 * @description <p>APP用户文章列 </p>
 *
 * @version 1.0
 * @package com.leadnews.user.pojo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ap_user_article_list")
@ApiModel(value="ApUserArticleList", description="APP用户文章列")
public class ApUserArticleList implements Serializable {


    @ApiModelProperty(notes = "主键", dataType="Long")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(notes = "用户ID", dataType="Long")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty(notes = "频道ID", dataType="Integer")
    @TableField("channel_id")
    private Integer channelId;

    @ApiModelProperty(notes = "动态ID", dataType="Long")
    @TableField("article_id")
    private Long articleId;

    @ApiModelProperty(notes = "是否展示", dataType="Integer")
    @TableField("is_show")
    private Integer isShow;

    @ApiModelProperty(notes = "推荐时间", dataType="LocalDateTime")
    @TableField("recommend_time")
    private LocalDateTime recommendTime;

    @ApiModelProperty(notes = "是否阅读", dataType="Integer")
    @TableField("is_read")
    private Integer isRead;

    @ApiModelProperty(notes = "推荐算法", dataType="Integer")
    @TableField("strategy_id")
    private Integer strategyId;


}
