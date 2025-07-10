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
import java.time.LocalDateTime;

/**
 * @description <p>自媒体用户信息 </p>
 *
 * @version 1.0
 * @package com.leadnews.wemedia.pojo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("wm_user")
@ApiModel(value="WmUser", description="自媒体用户信息")
public class WmUser implements Serializable {


    @ApiModelProperty(notes = "主键", dataType="Long")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(notes = "app端用户id", dataType="Long")
    @TableField("ap_user_id")
    private Long apUserId;

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

    @ApiModelProperty(notes = "归属地", dataType="String")
    @TableField("location")
    private String location;

    @ApiModelProperty(notes = "手机号", dataType="String")
    @TableField("phone")
    private String phone;

    @ApiModelProperty(notes = "状态 0:暂时不可用 1:永久不可用 9:正常可用", dataType="Integer")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(notes = "邮箱", dataType="String")
    @TableField("email")
    private String email;

    @ApiModelProperty(notes = "账号类型 0:个人 1:企业 2:子账号", dataType="Integer")
    @TableField("type")
    private Integer type;

    @ApiModelProperty(notes = "运营评分", dataType="Integer")
    @TableField("score")
    private Integer score;

    @ApiModelProperty(notes = "最后一次登录时间", dataType="LocalDateTime")
    @TableField("login_time")
    private LocalDateTime loginTime;

    @ApiModelProperty(notes = "创建时间", dataType="LocalDateTime")
    @TableField("created_time")
    private LocalDateTime createdTime;


}
