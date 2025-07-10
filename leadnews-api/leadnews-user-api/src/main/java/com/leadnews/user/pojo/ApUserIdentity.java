package com.leadnews.user.pojo;

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
 * @description <p>APP身份认证信息 </p>
 *
 * @version 1.0
 * @package com.leadnews.user.pojo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ap_user_identity")
@ApiModel(value="ApUserIdentity", description="APP身份认证信息")
public class ApUserIdentity implements Serializable {


    @ApiModelProperty(notes = "主键", dataType="Integer")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(notes = "账号ID", dataType="Integer")
    @TableField("user_id")
    private Integer userId;

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

    @ApiModelProperty(notes = "行业", dataType="String")
    @TableField("industry")
    private String industry;

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
