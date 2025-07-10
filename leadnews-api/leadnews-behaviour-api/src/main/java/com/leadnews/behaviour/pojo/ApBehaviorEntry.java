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
 * @description <p>APP行为实体,一个行为实体可能是用户或者设备，或者其它 </p>
 *
 * @version 1.0
 * @package com.leadnews.behaviour.pojo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ap_behavior_entry")
@ApiModel(value="ApBehaviorEntry", description="APP行为实体,一个行为实体可能是用户或者设备，或者其它")
public class ApBehaviorEntry implements Serializable {


    @ApiModelProperty(notes = "主键", dataType="Long")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(notes = "实体类型 0:终端设备 1:用户", dataType="Integer")
    @TableField("type")
    private Integer type;

    @ApiModelProperty(notes = "实体ID", dataType="Long")
    @TableField("entry_id")
    private Long entryId;

    @ApiModelProperty(notes = "创建时间", dataType="LocalDateTime")
    @TableField("created_time")
    private LocalDateTime createdTime;


}
