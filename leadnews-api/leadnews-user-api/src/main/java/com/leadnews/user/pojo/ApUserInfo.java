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
 * @description <p>APP用户详情信息 </p>
 *
 * @version 1.0
 * @package com.leadnews.user.pojo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ap_user_info")
@ApiModel(value="ApUserInfo", description="APP用户详情信息")
public class ApUserInfo implements Serializable {


    @ApiModelProperty(notes = "主键", dataType="Long")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("user_id")
    private Long userId;

    @ApiModelProperty(notes = "真是姓名", dataType="String")
    @TableField("name")
    private String name;

    @ApiModelProperty(notes = "身份证号,aes加密", dataType="String")
    @TableField("idno")
    private String idno;

    @ApiModelProperty(notes = "公司", dataType="String")
    @TableField("company")
    private String company;

    @ApiModelProperty(notes = "职业", dataType="String")
    @TableField("occupation")
    private String occupation;

    @ApiModelProperty(notes = "年龄", dataType="Integer")
    @TableField("age")
    private Integer age;

    @TableField("birthday")
    private LocalDateTime birthday;

    @ApiModelProperty(notes = "个人格言", dataType="String")
    @TableField("introduction")
    private String introduction;

    @ApiModelProperty(notes = "归属地", dataType="String")
    @TableField("location")
    private String location;

    @ApiModelProperty(notes = "粉丝数量", dataType="Integer")
    @TableField("fans")
    private Integer fans;

    @ApiModelProperty(notes = "关注数量", dataType="Integer")
    @TableField("follows")
    private Integer follows;

    @ApiModelProperty(notes = "是否允许推荐我给好友", dataType="Integer")
    @TableField("is_recommend_me")
    private Integer isRecommendMe;

    @ApiModelProperty(notes = "是否允许给我推荐好友", dataType="Integer")
    @TableField("is_recommend_friend")
    private Integer isRecommendFriend;

    @ApiModelProperty(notes = "是否分享页面显示头像", dataType="Integer")
    @TableField("is_display_image")
    private Integer isDisplayImage;

    @ApiModelProperty(notes = "更新时间", dataType="LocalDateTime")
    @TableField("updated_time")
    private LocalDateTime updatedTime;


}
