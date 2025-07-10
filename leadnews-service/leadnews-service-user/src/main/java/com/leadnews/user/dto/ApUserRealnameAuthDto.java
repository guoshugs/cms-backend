package com.leadnews.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @version 1.0
 * @description 说明
 * @package com.leadnews.user.dto
 */
@Data
@ApiModel(value = "实名认证审核驳回条件", description = "实名认证审核驳回条件")
public class ApUserRealnameAuthDto {
    @ApiModelProperty(notes = "实名认证的记录id", dataType = "Integer")
    private Long id;
    @ApiModelProperty(notes = "驳回的原因",dataType = "String")
    private String msg;
}
