package com.leadnews.common.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @version 1.0
 * @description 说明
 * @package com.leadnews.common.dto
 */
@Data
public class LoginUserDto {
    @NotEmpty(message = "用户名不能为空")
    private String name;
    @NotEmpty(message = "密码为空")
    private String password;
}
