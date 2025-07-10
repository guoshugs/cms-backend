package com.leadnews.user.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description <p>APP用户私信信息 </p>
 *
 * @version 1.0
 * @package com.leadnews.user.pojo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ap_user_letter")
@ApiModel(value="ApUserLetter", description="APP用户私信信息")
public class ApUserLetter implements Serializable {


    @ApiModelProperty(notes = "主键", dataType="Long")
    @TableField("id")
    private Long id;

    @ApiModelProperty(notes = "用户ID", dataType="Long")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty(notes = "发送人ID", dataType="Long")
    @TableField("sender_id")
    private Long senderId;

    @ApiModelProperty(notes = "发送人昵称", dataType="String")
    @TableField("sender_name")
    private String senderName;

    @ApiModelProperty(notes = "私信内容", dataType="String")
    @TableField("content")
    private String content;

    @ApiModelProperty(notes = "是否阅读", dataType="Integer")
    @TableField("is_read")
    private Integer isRead;

    @ApiModelProperty(notes = "创建时间", dataType="LocalDateTime")
    @TableField("created_time")
    private LocalDateTime createdTime;

    @ApiModelProperty(notes = "阅读时间", dataType="LocalDateTime")
    @TableField("read_time")
    private LocalDateTime readTime;


}
