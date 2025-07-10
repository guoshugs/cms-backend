package com.leadnews.wemedia.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @version 1.0
 * @description 说明
 * @package com.leadnews.wemedia.dto
 */
@Data
@ApiModel(description = "文章内容 数据类型")
public class WmNewsContentNodeDto {
    //type 指定类型  text 标识文本   image 标识 图片
    @ApiModelProperty(notes = "节点类型：image代表内容为图片， text代表内容为文本", dataType="String")
    private String type;
    //value 指定内容
    @ApiModelProperty(notes = "节点内容值：type=image时为图片地址， type=text代表内容为文本", dataType="String")
    private String value;
}
