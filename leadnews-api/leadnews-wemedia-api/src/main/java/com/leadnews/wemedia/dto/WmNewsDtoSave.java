package com.leadnews.wemedia.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@ApiModel(description = "发布文章提交对象数据格式")
public class WmNewsDtoSave {
    //主键ID
    @ApiModelProperty(notes="文章id，存在则代表更新")
    private Long id;

    //文章标题
    @ApiModelProperty(notes="文章标题")
    private String title;

    //图文内容
    @ApiModelProperty(notes="图文内容")
    private String content;

    //指定为封面类型  0 是无图  1 是单图  3 是多图  -1 是自动
    @ApiModelProperty(notes="封面类型")
    private Integer type;

    //指定选中的频道ID
    @ApiModelProperty(notes="所属频道id")
    private Integer channelId;

    //指定标签
    @ApiModelProperty(notes="指定的标签")
    private String labels;

    //状态 0 草稿  1 提交 待审核 （该字段可以不用设置,前端不必传递）
    @ApiModelProperty(notes="文章提交类型：0=草稿，1=提交待审核。前端可不传")
    private Integer status;

    //定时发布时间
    @ApiModelProperty(notes="指定发布的时间")
    private LocalDateTime publishTime;

    //封面图片
    @ApiModelProperty(notes="封面图片")
    private List<String> images; // 注意类型！

}