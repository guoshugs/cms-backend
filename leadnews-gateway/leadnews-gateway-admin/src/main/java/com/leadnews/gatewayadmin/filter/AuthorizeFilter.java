package com.leadnews.gatewayadmin.filter;


import com.leadnews.common.constants.SC;
import com.leadnews.common.util.AppJwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @version 1.0
 * @description 网关统一鉴权
 * @package com.leadnews.gatewayadmin.filters
 */
@Component
@Slf4j
public class AuthorizeFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //获取请求对象
        ServerHttpRequest req = exchange.getRequest();
        //获取响应对象
        ServerHttpResponse res = exchange.getResponse();
        //白名单放行判断
        //1. 获取请求过来的url地址
        String path = req.getURI().getPath();
        log.debug("请求的地址：{}", path);
        //2. 判断地址是否是后台的登录地址
        if(path.startsWith("/admin/login")){
            //是则放行
            return chain.filter(exchange);
        }
        //token校验
        //1. 获取token
        String token = req.getHeaders().getFirst("token");
        //2. 非空判断
        if(StringUtils.isNotEmpty(token)){
            //3. 验证token
            int verifyToken = AppJwtUtil.verifyToken(token);
            if(SC.JWT_OK==verifyToken){
                //4. 验证通过则放行
                return chain.filter(exchange);
            }
        }
        //5. 没有token，或验证没通过的，阻止请求。响应未登录
        res.setStatusCode(HttpStatus.UNAUTHORIZED);
        return res.setComplete(); //setComplete说请求已经结束了，不再转了
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
