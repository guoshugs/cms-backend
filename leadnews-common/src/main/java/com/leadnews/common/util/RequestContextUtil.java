package com.leadnews.common.util;

import com.leadnews.common.constants.SC;
import com.leadnews.common.exception.LeadNewsException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class RequestContextUtil {
    /**
     * 获取登录的用户的ID 可以是自媒体账号 也可以是 平台账号 也可以是app账号
     * @return
     */
    public static Long getUserId(){
        String loginUserId = RequestContextUtil.getHeader(SC.USER_HEADER_NAME);
        try {
            return Long.parseLong(loginUserId);
        } catch (NumberFormatException e) {
            throw new LeadNewsException("兄弟：获取请求头中的用户id失败了！检查登录成功后是否把用户id存入token的载荷中!");
        }
    }

    public static String getHeader(String headerName){
        // RequestContextHolder: 请求对象的上下文持有者, 一次线程执行过程.都有请求对象的相关信息, 使用了ThreadLocal
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        // 请求对象
        HttpServletRequest request = requestAttributes.getRequest();
        //获取路由转发的头信息
        String headerValue = request.getHeader(headerName);
        return headerValue;
    }

    public static boolean isAnonymous() {
        return RequestContextUtil.getUserId() == SC.ANONYMOUS_USER_ID;
    }
}