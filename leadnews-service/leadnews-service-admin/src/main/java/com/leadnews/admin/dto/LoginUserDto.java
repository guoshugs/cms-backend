package com.leadnews.admin.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 前端用户登录传来的用户名和密码封装成LoginUserDto对象来接收
 * 也许发现就传来2个字符串，可以用Map来接收，是可以。但是将来前端传来任何参数，后端都会接收。
 * 接下来有被注入的风险。另外看代码的人就要去猜Map的含义。做参数校验的时候，要从Map中取出才能做校验。
 * 用类来接收，可以使用校验框架Hibernate Validator的注解@NotEmpty@NotBlank就可以
 * @version 1.0
 * @description 说明
 * @package com.leadnews.common.dto
 */
@Data
public class LoginUserDto {
    @NotEmpty(message = "用户名不能为空！")
    private String name;
    @NotEmpty(message = "密码不能为空！")
    private String password;
}
/* 登录功能是一个特殊的业务逻辑，不属于后台正常的业务逻辑，有点类似权限的管理
* 登录的请求地址：前面部分属于Nginx/中间部分属于网关/后面部分才是请求路径
* 但是登录接口并没有api、v1、v2
* 基于以上原因，登录接口在接收请求的时候，专门定义一个LoginController，
* 不管任何人都可以来登录，是放开open的。不需要权限控制。
* 所以独立开来，不要把登录功能放到属于后台管理系统的业务里面。像系统设置、系统功能一样。
* 所以admin/login代表网关转发。/in, /out代表登录退出。
*/
