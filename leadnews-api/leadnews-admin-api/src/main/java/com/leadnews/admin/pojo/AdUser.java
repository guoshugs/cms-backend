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
 * @description <p>管理员用户信息 </p>
 *
 * @version 1.0
 * @package com.leadnews.admin.pojo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ad_user")
@ApiModel(value="AdUser", description="管理员用户信息")
public class AdUser implements Serializable {


    @ApiModelProperty(notes = "主键", dataType="Long")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(notes = "登录用户名", dataType="String")
    @TableField("name")
    private String name;

    @ApiModelProperty(notes = "登录密码", dataType="String")
    @TableField("password")
    private String password;

    @ApiModelProperty(notes = "盐", dataType="String")
    @TableField("salt")
    private String salt;

    @ApiModelProperty(notes = "昵称", dataType="String")
    @TableField("nickname")
    private String nickname;

    @ApiModelProperty(notes = "头像", dataType="String")
    @TableField("image")
    private String image;

    @ApiModelProperty(notes = "手机号", dataType="String")
    @TableField("phone")
    private String phone;

    @ApiModelProperty(notes = "状态 0:暂时不可用 1:永久不可用 9:正常可用", dataType="Integer")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(notes = "邮箱", dataType="String")
    @TableField("email")
    private String email;

    @ApiModelProperty(notes = "最后一次登录时间", dataType="LocalDateTime")
    @TableField("login_time")
    private LocalDateTime loginTime;

    @ApiModelProperty(notes = "创建时间", dataType="LocalDateTime")
    @TableField("created_time")
    private LocalDateTime createdTime;


}
