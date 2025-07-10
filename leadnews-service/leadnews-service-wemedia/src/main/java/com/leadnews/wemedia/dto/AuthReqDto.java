package com.leadnews.wemedia.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @version 1.0
 * @description
 * 前端提交：
 * {id:10,msg: abc, status:}
 *
 * springMVC默认的json转换器
 * jackson
 * 0:false, 雌， 1：true, 雄
 * 后台用
 * Map<String,Object> map = new LinkedHashMap();
 * Object 不知道具体的数据类型是什么？
 * Long
 *     推断：
 *        判断是数字吗?
 *          如果是数字
 *             long默认
 *                数值属于哪个范围 在Integer.Max_value 使用Integer
 *
 * map.put("id", Integer.parseInt(10))
 * @package com.leadnews.wemedia.dto
 */
@Data
public class AuthReqDto {
    @NotNull(message = "参数缺失")
    private Long id;
    private String msg;
    // 前端传过来的status是不可信的，可以随时修改的，所以不需要status，要自己设置
}
