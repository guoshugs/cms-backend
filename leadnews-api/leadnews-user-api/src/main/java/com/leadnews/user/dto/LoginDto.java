package com.leadnews.user.dto;

import lombok.Data;

/** 这个dto和其他的dto不同，就放在了用户微服务内部，其他都是放在远程调用的包中。
 * 以供其他微服务使用。而登录的dto只有User用到。
 * @version 1.0
 * @description 说明
 * @package com.leadnews.user.dto
 */
@Data
public class LoginDto {
    // 手机号码
    private String phone;
    // 密码
    private String password;
    // 设备码
    private String equipmentId;
}
