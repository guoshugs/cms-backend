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
 * @description <p>访问数据统计 </p>
 *
 * @version 1.0
 * @package com.leadnews.admin.pojo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ad_vistor_statistics")
@ApiModel(value="AdVistorStatistics", description="访问数据统计")
public class AdVistorStatistics implements Serializable {


    @ApiModelProperty(notes = "主键", dataType="Integer")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(notes = "日活", dataType="Integer")
    @TableField("activity")
    private Integer activity;

    @ApiModelProperty(notes = "访问量", dataType="Integer")
    @TableField("vistor")
    private Integer vistor;

    @ApiModelProperty(notes = "IP量", dataType="Integer")
    @TableField("ip")
    private Integer ip;

    @ApiModelProperty(notes = "注册量", dataType="Integer")
    @TableField("register")
    private Integer register;

    @ApiModelProperty(notes = "创建时间", dataType="LocalDateTime")
    @TableField("created_time")
    private LocalDateTime createdTime;


}
