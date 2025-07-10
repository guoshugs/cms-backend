package com.leadnews.core.controller;

import com.leadnews.common.vo.ResultVo;

/**
 * @author ericye
 * @version 1.0
 * @description 标题
 * @package com.leadnews.core.controller
 */
public interface IInsertController<T> {
    /**
     * 添加记录
     * @param record
     * @return
     */
    ResultVo insert(T record);

}