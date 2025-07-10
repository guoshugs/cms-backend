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
 * @description <p>APP文章作者信息 </p>
 *
 * @version 1.0
 * @package com.leadnews.article.pojo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ap_author")
@ApiModel(value="ApAuthor", description="APP文章作者信息")
public class ApAuthor implements Serializable {


    @ApiModelProperty(notes = "主键", dataType="Long")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(notes = "作者名称", dataType="String")
    @TableField("name")
    private String name;

    @ApiModelProperty(notes = "0:爬取数据 1:签约合作商 2:平台自媒体人", dataType="Integer")
    @TableField("type")
    private Integer type;

    @ApiModelProperty(notes = "社交账号ID", dataType="Long")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty(notes = "创建时间", dataType="LocalDateTime")
    @TableField("created_time")
    private LocalDateTime createdTime;

    @ApiModelProperty(notes = "自媒体账号", dataType="Long")
    @TableField("wm_user_id")
    private Long wmUserId;


}
