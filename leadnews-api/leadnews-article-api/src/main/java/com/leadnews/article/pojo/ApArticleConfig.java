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

/**
 * @description <p>APP已发布文章配置 </p>
 *
 * @version 1.0
 * @package com.leadnews.article.pojo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ap_article_config")
@ApiModel(value="ApArticleConfig", description="APP已发布文章配置")
public class ApArticleConfig implements Serializable {


    @ApiModelProperty(notes = "主键", dataType="Long")
    @TableId(value = "id", type = IdType.INPUT)
    @JsonSerialize(using = Long2StringSerializer.class)
    private Long id;

    @ApiModelProperty(notes = "文章ID", dataType="Long")
    @TableField("article_id")
    @JsonSerialize(using = Long2StringSerializer.class)
    private Long articleId;

    @ApiModelProperty(notes = "是否可评论", dataType="Integer")
    @TableField("is_comment")
    private Integer isComment;

    @ApiModelProperty(notes = "是否转发", dataType="Integer")
    @TableField("is_forward")
    private Integer isForward;

    @ApiModelProperty(notes = "是否下架", dataType="Integer")
    @TableField("is_down")
    private Integer isDown;

    @ApiModelProperty(notes = "是否已删除", dataType="Integer")
    @TableField("is_delete")
    private Integer isDelete;


}
