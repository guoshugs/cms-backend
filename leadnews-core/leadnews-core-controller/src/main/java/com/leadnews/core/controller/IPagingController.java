package com.leadnews.core.controller;

import com.leadnews.common.dto.PageRequestDto;
import com.leadnews.common.vo.PageResultVo;

/**
 * @version 1.0
 * @description 标题
 * @package com.leadnews.core.controller
 */
public interface IPagingController<T> {

    /**
     * 根据查询条件 dto 分页查询
     * @return
     */
    PageResultVo findByPage(PageRequestDto dto);
}
