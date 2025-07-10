package com.leadnews.search.dto;

import lombok.Data;

import java.util.Date;

/**
 * @version 1.0
 * @description 说明
 * @package com.leadnews.search.dto
 */
@Data
public class SearchDto {

    // 设备ID
    private Integer equipmentId;
    /**
     * 搜索关键字
     */
    private String searchWords;
    /**
     * 当前页
     */
    private Integer pageNum;
    /**
     * 分页条数
     */
    private Integer pageSize;

    /**
     * 最后一条记录的时间
     */
    private Date minBehotTime;

    private String tag;
}
