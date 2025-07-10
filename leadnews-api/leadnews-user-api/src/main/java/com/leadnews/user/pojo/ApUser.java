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
 * @description <p>APP用户信息 </p>
 *
 * @version 1.0
 * @package com.leadnews.user.pojo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ap_user")
@ApiModel(value="ApUser", description="APP用户信息")
public class ApUser implements Serializable {


    @ApiModelProperty(notes = "主键", dataType="Long")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(notes = "密码、通信等加密盐", dataType="String")
    @TableField("salt")
    private String salt;

    @ApiModelProperty(notes = "用户名", dataType="String")
    @TableField("name")
    private String name;

    @ApiModelProperty(notes = "密码,md5加密", dataType="String")
    @TableField("password")
    private String password;

    @ApiModelProperty(notes = "手机号", dataType="String")
    @TableField("phone")
    private String phone;

    @ApiModelProperty(notes = "头像", dataType="String")
    @TableField("image")
    private String image;

    @ApiModelProperty(notes = "0:男 1:女 2:未知", dataType="Integer")
    @TableField("sex")
    private Integer sex;

    @ApiModelProperty(notes = "是否有证书认证 0:未，1:是", dataType="Integer")
    @TableField("is_certification")
    private Integer isCertification;

    @ApiModelProperty(notes = "0:否 1:是", dataType="Integer")
    @TableField("is_identity_authentication")
    private Integer isIdentityAuthentication;

    @ApiModelProperty(notes = "0:正常 1:锁定", dataType="Integer")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(notes = "0:普通用户 1:自媒体人 2:大V", dataType="Integer")
    @TableField("flag")
    private Integer flag;

    @ApiModelProperty(notes = "注册时间", dataType="LocalDateTime")
    @TableField("created_time")
    private LocalDateTime createdTime;


}
