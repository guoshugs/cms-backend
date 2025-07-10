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
 * @description <p>APP用户消息通知信息 </p>
 *
 * @version 1.0
 * @package com.leadnews.user.pojo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ap_user_message")
@ApiModel(value="ApUserMessage", description="APP用户消息通知信息")
public class ApUserMessage implements Serializable {


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

    @ApiModelProperty(notes = "消息类型 0:关注 1:取消关注 2:点赞文章 3:取消点赞文章 4:转发文章 5:收藏文章 6:点赞评论 7:审核通过评论 8:私信通知 9:评论通知 10:分享通知  100:身份证审核通过 101:身份证审核拒绝 102:实名认证通过 103:实名认证失败 104:自媒体人祝贺 105:异常登录通知 106:反馈回复 107:转发通知", dataType="Integer")
    @TableField("type")
    private Integer type;

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
