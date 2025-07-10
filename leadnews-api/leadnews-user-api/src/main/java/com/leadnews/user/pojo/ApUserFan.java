package com.leadnews.user.pojo;

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
 * @description <p>APP用户粉丝信息 </p>
 *
 * @version 1.0
 * @package com.leadnews.user.pojo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ap_user_fan")
@ApiModel(value="ApUserFan", description="APP用户粉丝信息")
public class ApUserFan implements Serializable {


    @ApiModelProperty(notes = "主键", dataType="Long")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(notes = "用户ID", dataType="Long")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty(notes = "粉丝ID", dataType="Long")
    @TableField("fans_id")
    private Long fansId;

    @ApiModelProperty(notes = "粉丝昵称", dataType="String")
    @TableField("fans_name")
    private String fansName;

    @ApiModelProperty(notes = "粉丝忠实度 0:正常 1:潜力股 2:勇士 3:铁杆 4:老铁", dataType="Integer")
    @TableField("level")
    private Integer level;

    @ApiModelProperty(notes = "创建时间", dataType="LocalDateTime")
    @TableField("created_time")
    private LocalDateTime createdTime;

    @ApiModelProperty(notes = "是否可见我动态", dataType="Integer")
    @TableField("is_display")
    private Integer isDisplay;

    @ApiModelProperty(notes = "是否屏蔽私信", dataType="Integer")
    @TableField("is_shield_letter")
    private Integer isShieldLetter;

    @ApiModelProperty(notes = "是否屏蔽评论", dataType="Integer")
    @TableField("is_shield_comment")
    private Integer isShieldComment;


}
