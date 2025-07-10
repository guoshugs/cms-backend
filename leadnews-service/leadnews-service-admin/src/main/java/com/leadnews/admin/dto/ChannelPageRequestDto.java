package com.leadnews.admin.dto;

import com.leadnews.common.dto.PageRequestDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @version 1.0
 * @description 说明
 * @package com.leadnews.admin.dto
 */
@Data
@ApiModel(value = "频道分页查询条件", description = "频道分页查询条件")
public class ChannelPageRequestDto extends PageRequestDto {
    @ApiModelProperty(notes = "搜索条件：名称", dataType = "String")
    private String name;
    @ApiModelProperty(notes = "频道的状态：是否可用", dataType = "Integer")
    private Integer status;
}
