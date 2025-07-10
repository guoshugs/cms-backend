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
 * @description <p>APP不喜欢行为 </p>
 *
 * @version 1.0
 * @package com.leadnews.behaviour.pojo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ap_unlikes_behavior")
@ApiModel(value="ApUnlikesBehavior", description="APP不喜欢行为")
public class ApUnlikesBehavior implements Serializable {


    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(notes = "实体ID", dataType="Long")
    @TableField("entry_id")
    private Long entryId;

    @ApiModelProperty(notes = "文章ID", dataType="Long")
    @TableField("article_id")
    private Long articleId;

    @ApiModelProperty(notes = "0:不喜欢 1:取消不喜欢", dataType="Integer")
    @TableField("type")
    private Integer type;

    @ApiModelProperty(notes = "登录时间", dataType="LocalDateTime")
    @TableField("created_time")
    private LocalDateTime createdTime;


}
