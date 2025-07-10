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
 * @description <p>APP用户反馈信息 </p>
 *
 * @version 1.0
 * @package com.leadnews.user.pojo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ap_user_feedback")
@ApiModel(value="ApUserFeedback", description="APP用户反馈信息")
public class ApUserFeedback implements Serializable {


    @ApiModelProperty(notes = "主键", dataType="Long")
    @TableField("id")
    private Long id;

    @ApiModelProperty(notes = "用户ID", dataType="Long")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty(notes = "发送人昵称", dataType="String")
    @TableField("user_name")
    private String userName;

    @ApiModelProperty(notes = "私信内容", dataType="String")
    @TableField("content")
    private String content;

    @ApiModelProperty(notes = "反馈图片,多张逗号分隔", dataType="String")
    @TableField("images")
    private String images;

    @ApiModelProperty(notes = "是否阅读", dataType="Integer")
    @TableField("is_solve")
    private Integer isSolve;

    @TableField("solve_note")
    private String solveNote;

    @ApiModelProperty(notes = "阅读时间", dataType="LocalDateTime")
    @TableField("solved_time")
    private LocalDateTime solvedTime;

    @ApiModelProperty(notes = "创建时间", dataType="LocalDateTime")
    @TableField("created_time")
    private LocalDateTime createdTime;


}
