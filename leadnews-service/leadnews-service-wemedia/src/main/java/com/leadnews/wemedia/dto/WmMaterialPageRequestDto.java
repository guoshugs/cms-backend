package com.leadnews.wemedia.dto;

import com.leadnews.common.dto.PageRequestDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @version 1.0
 * @description 说明
 * @package com.leadnews.wemedia.dto
 */
@Data
@ApiModel(value = "WmMaterialPageRequestDto", description = "素材分页查询条件")
public class WmMaterialPageRequestDto extends PageRequestDto {
    /**
     * 是否是收藏
     */
    @ApiModelProperty(notes = "是否是收藏", dataType = "Integer")
    private Integer isCollection; // boolean也是可以的，因为sql会把boolean识别为integer
}
