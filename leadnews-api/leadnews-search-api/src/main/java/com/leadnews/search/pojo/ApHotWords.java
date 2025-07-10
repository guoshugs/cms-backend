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
 * @description <p>搜索热词 </p>
 *
 * @version 1.0
 * @package com.leadnews.search.pojo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ap_hot_words")
@ApiModel(value="ApHotWords", description="搜索热词")
public class ApHotWords implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(notes = "热词", dataType="String")
    @TableField("hot_words")
    private String hotWords;

    @ApiModelProperty(notes = "0:热,1:荐,2:新,3:火,4:精,5:亮", dataType="Integer")
    @TableField("type")
    private Integer type;

    @ApiModelProperty(notes = "热词日期", dataType="String")
    @TableField("hot_date")
    private String hotDate;

    @ApiModelProperty(notes = "创建时间", dataType="LocalDateTime")
    @TableField("created_time")
    private LocalDateTime createdTime;


}
