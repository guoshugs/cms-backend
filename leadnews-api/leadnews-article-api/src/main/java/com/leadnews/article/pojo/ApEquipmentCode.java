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

/**
 * @description <p>APP设备码信息 </p>
 *
 * @version 1.0
 * @package com.leadnews.article.pojo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ap_equipment_code")
@ApiModel(value="ApEquipmentCode", description="APP设备码信息")
public class ApEquipmentCode implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(notes = "用户ID", dataType="Long")
    @TableField("equipment_id")
    private Long equipmentId;

    @ApiModelProperty(notes = "设备码", dataType="String")
    @TableField("code")
    private String code;


}
