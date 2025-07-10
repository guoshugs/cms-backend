package com.leadnews.core.controller;

import com.leadnews.common.vo.ResultVo;

/**
 * @version 1.0
 * @description 标题
 * @package com.leadnews.core.controller
 */
public interface IUpdateController<T> {

    /**
     * 根据对象进行更新 根据ID
     *
     * @param record
     * @return
     */
    ResultVo updateByPrimaryKey(T record);
}
