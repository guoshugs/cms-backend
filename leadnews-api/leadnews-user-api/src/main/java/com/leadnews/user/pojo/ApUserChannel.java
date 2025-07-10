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
 * @description <p>APP用户频道信息 </p>
 *
 * @version 1.0
 * @package com.leadnews.user.pojo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ap_user_channel")
@ApiModel(value="ApUserChannel", description="APP用户频道信息")
public class ApUserChannel implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(notes = "用户ID", dataType="Integer")
    @TableField("channel_id")
    private Integer channelId;

    @ApiModelProperty(notes = "文章ID", dataType="Long")
    @TableField("user_id")
    private Long userId;

    @TableField("name")
    private String name;

    @ApiModelProperty(notes = "频道排序", dataType="Integer")
    @TableField("ord")
    private Integer ord;

    @ApiModelProperty(notes = "登录时间", dataType="LocalDateTime")
    @TableField("created_time")
    private LocalDateTime createdTime;


}
