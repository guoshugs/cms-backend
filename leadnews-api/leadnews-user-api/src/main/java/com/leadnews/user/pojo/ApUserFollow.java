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
 * @description <p>APP用户关注信息 </p>
 *
 * @version 1.0
 * @package com.leadnews.user.pojo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ap_user_follow")
@ApiModel(value="ApUserFollow", description="APP用户关注信息")
public class ApUserFollow implements Serializable {


    @ApiModelProperty(notes = "主键", dataType="Long")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(notes = "用户ID", dataType="Long")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty(notes = "关注作者ID", dataType="Long")
    @TableField("follow_id")
    private Long followId;

    @ApiModelProperty(notes = "粉丝昵称", dataType="String")
    @TableField("follow_name")
    private String followName;

    @ApiModelProperty(notes = "关注度 0:偶尔感兴趣 1:一般 2:经常 3:高度", dataType="Integer")
    @TableField("level")
    private Integer level;

    @ApiModelProperty(notes = "是否动态通知", dataType="Integer")
    @TableField("is_notice")
    private Integer isNotice;

    @ApiModelProperty(notes = "创建时间", dataType="LocalDateTime")
    @TableField("created_time")
    private LocalDateTime createdTime;


}
