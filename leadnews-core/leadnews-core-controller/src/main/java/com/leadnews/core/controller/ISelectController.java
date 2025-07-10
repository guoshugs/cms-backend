package com.leadnews.core.controller;

import com.leadnews.common.vo.ResultVo;

import java.beans.IntrospectionException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author ericye
 * @version 1.0
 * @description 标题
 * @package com.leadnews.core.controller
 */
public interface ISelectController<T> {

    /**
     * 根据ID 获取信息
     * @param id
     * @return
     */
    ResultVo<T> findById(Serializable id);

    /**
     * 根据ID 获取信息列表
     * @return
     */
    ResultVo<List<T>> findAll();

    /**
     * 根据条件查询   where xxx=? and yyy=?
     * @param record
     * @return
     */
    ResultVo<List<T>> findByRecord(T record) throws IntrospectionException, InvocationTargetException, IllegalAccessException;

}
