package com.leadnews.common.exception;

import com.leadnews.common.enums.HttpCodeEnum;
import lombok.NoArgsConstructor;

/**
 * @version 1.0
 * @description 自定义异常，用于区分系统异常与用户友好提示
 * @package com.leadnews.common.exception
 */
@NoArgsConstructor
public class LeadNewsException extends RuntimeException{
    /**
     * 错误的状态码
     */
    private Integer code=500;

    /**
     * 错误提示信息
     */
    private String message;

    public LeadNewsException(String message){
        super(message);
        this.message = message;
    }

    public LeadNewsException(Integer code, String message){
        super(message);
        this.code = code;
        this.message = message;
    }

    public LeadNewsException(HttpCodeEnum codeEnum){
        super(codeEnum.getMessage());
        this.code = codeEnum.getCode();
        this.message = codeEnum.getMessage();
    }

}
