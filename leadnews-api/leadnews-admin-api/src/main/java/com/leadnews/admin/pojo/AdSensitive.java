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
 * @description <p>敏感词信息 </p>
 *
 * @version 1.0
 * @package com.leadnews.admin.pojo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ad_sensitive")
@ApiModel(value="AdSensitive", description="敏感词信息")
public class AdSensitive implements Serializable {


    @ApiModelProperty(notes = "主键", dataType="Integer")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(notes = "敏感词", dataType="String")
    @TableField("sensitives")
    private String sensitives;

    @ApiModelProperty(notes = "创建时间", dataType="LocalDateTime")
    @TableField("created_time")
    private LocalDateTime createdTime;


}
