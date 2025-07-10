package com.leadnews.common.vo;

import lombok.Data;

/**
 * 用户登录校验成功后，返回前端Vo对象
 * @version 1.0
 * @description 说明
 * @package com.leadnews.common.vo
 */
@Data
public class LoginUserVo {
    // 返回的token
    private String token;
    // 返回的登录用户的信息
    // 为什么不指定类型？原因:app登录（ApUser）、admin也要登录(AdUser)、
    // 自媒体也有登录 WmUser。为了能接收这3个类型的对象，所以使用了Object
    private Object user;
    /* 这里使用Object来传递返回的用户信息。是因为不仅仅管理系统有user，
    * 自媒体系统、app端都有user，它们的用户类型都不一样。而他们都要实现登录。
    * 所以使用Object*/
}
