package com.leadnews.search.pojo;

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
 * @description <p>APP用户搜索信息 </p>
 *
 * @version 1.0
 * @package com.leadnews.search.pojo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ap_user_search")
@ApiModel(value="ApUserSearch", description="APP用户搜索信息")
public class ApUserSearch implements Serializable {


    @ApiModelProperty(notes = "主键", dataType="Long")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(notes = "用户ID", dataType="Long")
    @TableField("entry_id")
    private Long entryId;

    @ApiModelProperty(notes = "搜索词", dataType="String")
    @TableField("keyword")
    private String keyword;

    @ApiModelProperty(notes = "当前状态 0:无效 1:有效", dataType="Integer")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(notes = "创建时间", dataType="LocalDateTime")
    @TableField("created_time")
    private LocalDateTime createdTime;


}
