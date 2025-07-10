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
 * @description <p>热点文章 </p>
 *
 * @version 1.0
 * @package com.leadnews.article.pojo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ap_hot_articles")
@ApiModel(value="ApHotArticles", description="热点文章")
public class ApHotArticles implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("entry_id")
    private Long entryId;

    @ApiModelProperty(notes = "频道ID", dataType="Integer")
    @TableField("tag_id")
    private Integer tagId;

    @ApiModelProperty(notes = "频道名称", dataType="String")
    @TableField("tag_name")
    private String tagName;

    @ApiModelProperty(notes = "热度评分", dataType="Integer")
    @TableField("score")
    private Integer score;

    @ApiModelProperty(notes = "文章ID", dataType="Long")
    @TableField("article_id")
    private Long articleId;

    @ApiModelProperty(notes = "省市", dataType="Integer")
    @TableField("province_id")
    private Integer provinceId;

    @ApiModelProperty(notes = "市区", dataType="Integer")
    @TableField("city_id")
    private Integer cityId;

    @ApiModelProperty(notes = "区县", dataType="Integer")
    @TableField("county_id")
    private Integer countyId;

    @ApiModelProperty(notes = "是否阅读", dataType="Integer")
    @TableField("is_read")
    private Integer isRead;

    @TableField("release_date")
    private LocalDateTime releaseDate;

    @ApiModelProperty(notes = "创建时间", dataType="LocalDateTime")
    @TableField("created_time")
    private LocalDateTime createdTime;


}
