package com.leadnews.gatewaywemedia.filter;

import com.leadnews.common.constants.SC;
import com.leadnews.common.util.AppJwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
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
 * @description 说明
 * @package com.leadnews.gatewayadmin.filter
 */
@Component
@Slf4j
public class AuthorizeFilter implements GlobalFilter, Ordered {

    /**
     * 实现统一认证、鉴权，判断用户是否登录
     *
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    //1. 获取请求对象、响应对象
    ServerHttpRequest request = exchange.getRequest();
    ServerHttpResponse response = exchange.getResponse();
    //2. 获取请求路径
    String path = request.getURI().getPath();
    log.info("请求了路径:{}", path);
    //3. 判断请求路径是否为登录
    if (path.endsWith("/login/in")) {
        //4. 如果是登录则放行
        return chain.filter(exchange);
    }
    //5. 获取请求头的token
    String token = request.getHeaders().getFirst("token");
    //6. 校验有效性
    if (StringUtils.isNotEmpty(token)) {
        // 不为空，说明 token有值
       /* 验证token的有效性
       if (SC.JWT_OK == AppJwtUtil.verifyToken(token)) {
            return chain.filter(exchange);
        }
        int verifyToken(String token) {
            Claims claims = AppJwtUtil.getClaimsBody(token);
            return SC.JWT_OK;
        }
        这个verify方法，本身就是getClaimsBody*/
        //验证token的有效性
        try {
            // 解析token成功且不报错
            // claims载荷
            Claims claims = AppJwtUtil.getClaimsBody(token);
            Long loginUserId = claims.get("id", Long.class);
            // 存入请求头
            request.mutate().header(SC.USER_HEADER_NAME, loginUserId.toString());
            // mutate()方法的作用是根据输入创建一个新的对象，并根据需求对新对象进行修改或变换，而不改变原始对象的状态。
            // 有效则放行
            return chain.filter(exchange);
        } catch (ExpiredJwtException ex) {
            log.error("token已过期!", ex);
        } catch (Exception e) {
            log.error("token无效!", e);
        }
    }

    //8. 无效则响应401，让前端去登录
    response.setStatusCode(HttpStatus.UNAUTHORIZED);
    return response.setComplete();
    }

    @Override
    public int getOrder() {
        return 0;
    }

    public static void main(String[] args) {
        String token = "eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAACWL0QrDIAxF_yXPFQyzM_ZvokbmoCDEwsbYvzfd3u7h3POB5-ywgTBlyjU5XzC6UFpwjLQ64tqQShTfGBboPGHDO3m6rURpAT2y1frWKfvlVQ0f0vfrzUc14jFsy2v8yhTiv-zm8HsCY8Twd4AAAAA.r53iczQm5zIOfroPaJ_TpVy81jhqmlgmOLPOYXZjN63lPAyVb0y-yPEjfwitcoaZCjeDqKwY-UlMbbAvDOXnCQ";
        // Header, Payload, Signature
        Claims claims = AppJwtUtil.getClaimsBody(token);
        System.out.println(claims.toString());
    }
}
