package com.leadnews.admin.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description <p>管理员设备信息 </p>
 *
 * @version 1.0
 * @package com.leadnews.admin.pojo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ad_user_equipment")
@ApiModel(value="AdUserEquipment", description="管理员设备信息")
public class AdUserEquipment implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(notes = "用户ID", dataType="Long")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty(notes = "0:PC 1:Android 2:IOS 3:Pad 9:其他", dataType="Integer")
    @TableField("type")
    private Integer type;

    @ApiModelProperty(notes = "设备版本", dataType="String")
    @TableField("version")
    private String version;

    @ApiModelProperty(notes = "设备系统", dataType="String")
    @TableField("sys")
    private String sys;

    @ApiModelProperty(notes = "设备唯一号，MD5加密", dataType="String")
    @TableField("no")
    private String no;

    @ApiModelProperty(notes = "登录时间", dataType="LocalDateTime")
    @TableField("created_time")
    private LocalDateTime createdTime;


}
