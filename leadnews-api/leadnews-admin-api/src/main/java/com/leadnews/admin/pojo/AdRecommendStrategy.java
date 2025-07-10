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
 * @description <p>推荐策略信息 </p>
 *
 * @version 1.0
 * @package com.leadnews.admin.pojo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ad_recommend_strategy")
@ApiModel(value="AdRecommendStrategy", description="推荐策略信息")
public class AdRecommendStrategy implements Serializable {


    @ApiModelProperty(notes = "主键", dataType="Integer")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(notes = "策略名称", dataType="String")
    @TableField("name")
    private String name;

    @ApiModelProperty(notes = "策略描述", dataType="String")
    @TableField("description")
    private String description;

    @ApiModelProperty(notes = "是否有效", dataType="Integer")
    @TableField("is_enable")
    private Integer isEnable;

    @ApiModelProperty(notes = "分组ID", dataType="Integer")
    @TableField("group_id")
    private Integer groupId;

    @ApiModelProperty(notes = "创建时间", dataType="LocalDateTime")
    @TableField("created_time")
    private LocalDateTime createdTime;


}
