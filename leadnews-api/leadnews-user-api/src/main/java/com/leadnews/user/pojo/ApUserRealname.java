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
 * @description <p>APP实名认证信息 </p>
 *
 * @version 1.0
 * @package com.leadnews.user.pojo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ap_user_realname")
@ApiModel(value="ApUserRealname", description="APP实名认证信息")
public class ApUserRealname implements Serializable {


    @ApiModelProperty(notes = "主键", dataType="Long")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(notes = "账号ID", dataType="Long")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty(notes = "用户名称", dataType="String")
    @TableField("name")
    private String name;

    @ApiModelProperty(notes = "资源名称", dataType="String")
    @TableField("idno")
    private String idno;

    @ApiModelProperty(notes = "正面照片", dataType="String")
    @TableField("font_image")
    private String fontImage;

    @ApiModelProperty(notes = "背面照片", dataType="String")
    @TableField("back_image")
    private String backImage;

    @ApiModelProperty(notes = "手持照片", dataType="String")
    @TableField("hold_image")
    private String holdImage;

    @ApiModelProperty(notes = "活体照片", dataType="String")
    @TableField("live_image")
    private String liveImage;

    @ApiModelProperty(notes = "状态 0:创建中 1:待审核 2:审核失败 9:审核通过", dataType="Integer")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(notes = "拒绝原因", dataType="String")
    @TableField("reason")
    private String reason;

    @ApiModelProperty(notes = "创建时间", dataType="LocalDateTime")
    @TableField("created_time")
    private LocalDateTime createdTime;

    @ApiModelProperty(notes = "提交时间", dataType="LocalDateTime")
    @TableField("submited_time")
    private LocalDateTime submitedTime;

    @ApiModelProperty(notes = "更新时间", dataType="LocalDateTime")
    @TableField("updated_time")
    private LocalDateTime updatedTime;


}
