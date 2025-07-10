package com.leadnews.search.dto;

import lombok.Data;

@Data
public class HistoryLoadDto {
    // 设备ID
    private Integer equipmentId;
    /**
     * 分页条数
     */
    private Integer pageSize;

}
