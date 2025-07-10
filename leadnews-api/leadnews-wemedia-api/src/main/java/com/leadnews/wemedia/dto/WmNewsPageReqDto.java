package com.leadnews.wemedia.dto;

import com.leadnews.common.dto.PageRequestDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @version 1.0
 * @description 说明
 * @package com.leadnews.common.dto
 */
@Data
@ApiModel(description = "自媒体文章分页查询请求对象")
public class WmNewsPageReqDto extends PageRequestDto {
    /**
     * 状态
     */
    @ApiModelProperty(notes = "状态")
    private Short status;
    /**
     * 开始时间
     */
    @ApiModelProperty(notes = "发布开始时间")
    private Date beginPubDate;
    /**
     * 结束时间
     */
    @ApiModelProperty(notes = "发布结束时间")
    private Date endPubDate;
    /**
     * 所属频道ID
     */
    @ApiModelProperty(notes = "所属频道ID")
    private Integer channelId;
    /**
     * 关键字
     */
    @ApiModelProperty(notes = "文章标题关键字")
    private String keyword;

    /**
     * 标题
     */
    @ApiModelProperty(notes = "文章标题")
    private String title;
}
