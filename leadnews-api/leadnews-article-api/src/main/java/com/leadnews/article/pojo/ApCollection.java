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
 * @description <p>APP收藏信息 </p>
 *
 * @version 1.0
 * @package com.leadnews.article.pojo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ap_collection")
@ApiModel(value="ApCollection", description="APP收藏信息")
public class ApCollection implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
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

    @ApiModelProperty(notes = "创建时间", dataType="LocalDateTime")
    @TableField("collection_time")
    private LocalDateTime collectionTime;

    @ApiModelProperty(notes = "发布时间", dataType="LocalDateTime")
    @TableField("published_time")
    private LocalDateTime publishedTime;


}
