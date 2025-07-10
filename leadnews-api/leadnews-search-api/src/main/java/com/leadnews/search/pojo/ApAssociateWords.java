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
 * @description <p>联想词 </p>
 *
 * @version 1.0
 * @package com.leadnews.search.pojo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ap_associate_words")
@ApiModel(value="ApAssociateWords", description="联想词")
public class ApAssociateWords implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(notes = "联想词", dataType="String")
    @TableField("associate_words")
    private String associateWords;

    @ApiModelProperty(notes = "创建时间", dataType="LocalDateTime")
    @TableField("created_time")
    private LocalDateTime createdTime;


}
