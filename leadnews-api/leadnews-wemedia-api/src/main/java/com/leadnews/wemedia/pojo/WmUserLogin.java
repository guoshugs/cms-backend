package com.leadnews.wemedia.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @description <p>自媒体用户登录行为信息 </p>
 *
 * @version 1.0
 * @package com.leadnews.wemedia.pojo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("wm_user_login")
@ApiModel(value="WmUserLogin", description="自媒体用户登录行为信息")
public class WmUserLogin implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(notes = "用户ID", dataType="Long")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty(notes = "登录设备ID", dataType="Long")
    @TableField("equipment_id")
    private Long equipmentId;

    @ApiModelProperty(notes = "登录IP", dataType="String")
    @TableField("ip")
    private String ip;

    @ApiModelProperty(notes = "登录地址", dataType="String")
    @TableField("address")
    private String address;

    @ApiModelProperty(notes = "经度", dataType="BigDecimal")
    @TableField("longitude")
    private BigDecimal longitude;

    @ApiModelProperty(notes = "纬度", dataType="BigDecimal")
    @TableField("latitude")
    private BigDecimal latitude;

    @ApiModelProperty(notes = "登录时间", dataType="LocalDateTime")
    @TableField("created_time")
    private LocalDateTime createdTime;


}
