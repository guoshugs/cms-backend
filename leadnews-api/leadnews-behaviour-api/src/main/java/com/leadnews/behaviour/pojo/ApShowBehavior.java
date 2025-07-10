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
 * @description <p>APP文章展现行为 </p>
 *
 * @version 1.0
 * @package com.leadnews.behaviour.pojo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ap_show_behavior")
@ApiModel(value="ApShowBehavior", description="APP文章展现行为")
public class ApShowBehavior implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(notes = "实体ID", dataType="Long")
    @TableField("entry_id")
    private Long entryId;

    @ApiModelProperty(notes = "文章ID", dataType="Long")
    @TableField("article_id")
    private Long articleId;

    @ApiModelProperty(notes = "是否点击", dataType="Integer")
    @TableField("is_click")
    private Integer isClick;

    @ApiModelProperty(notes = "文章加载时间", dataType="LocalDateTime")
    @TableField("show_time")
    private LocalDateTime showTime;

    @ApiModelProperty(notes = "登录时间", dataType="LocalDateTime")
    @TableField("created_time")
    private LocalDateTime createdTime;


}
