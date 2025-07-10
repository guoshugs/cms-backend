package com.leadnews.admin.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description <p>频道标签信息 </p>
 *
 * @version 1.0
 * @package com.leadnews.admin.pojo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ad_channel_label")
@ApiModel(value="AdChannelLabel", description="频道标签信息")
public class AdChannelLabel implements Serializable {


    @ApiModelProperty(notes = "主键", dataType="Integer")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("channel_id")
    private Integer channelId;

    @ApiModelProperty(notes = "标签ID", dataType="Integer")
    @TableField("label_id")
    private Integer labelId;

    @ApiModelProperty(notes = "排序", dataType="Integer")
    @TableField("ord")
    private Integer ord;


}
