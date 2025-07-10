package com.leadnews.core.controller;

import com.leadnews.common.vo.ResultVo;

import java.io.Serializable;

/**
 * @version 1.0
 * @description 标题
 * @package com.leadnews.core.controller
 */
public interface IDeleteController<T> {

    /**
     * 根据ID 删除
     *
     * @param id
     * @return
     */
    ResultVo deleteById(Serializable id);
}
