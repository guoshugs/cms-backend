package com.leadnews.gatewayapp.filter;

import com.leadnews.common.constants.SC;
import com.leadnews.common.util.AppJwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.regex.Pattern;

@Component
@Slf4j
public class AppUserAuthorizeFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        String path = request.getURI().getPath();
        Pattern pattern = Pattern.compile("/login/.*");
        if (pattern.matcher(path).find()) {
            return chain.filter(exchange);
        }

        String token = request.getHeaders().getFirst("token");
        if (!StringUtils.isEmpty(token)) {
            int result = 0;
            try {
                Claims claimsBody = AppJwtUtil.getClaimsBody(token);
                Long loginUserId = claimsBody.get("id", Long.class);
                /*---------day10修改createToken方法后，登录成功时已经在载荷中存入头像和昵称----------*/
                String nickName = claimsBody.get(SC.LOGIN_USER_NAME, String.class);
                String headImage = claimsBody.get(SC.LOGIN_USER_IMAGE, String.class);

                // 存入请求头：USER_HEADER_NAME="userId"
                request.mutate().header(SC.USER_HEADER_NAME, loginUserId.toString());
                request.mutate().header(SC.LOGIN_USER_NAME, nickName);
                request.mutate().header(SC.LOGIN_USER_IMAGE, headImage);

                result = SC.JWT_OK;
            } catch (ExpiredJwtException ex) {
                result = SC.JWT_EXPIRE;
            } catch (Exception e){
                result = SC.JWT_FAIL;
            }

            if (SC.JWT_OK == result) {
                return chain.filter(exchange);
            }
        }
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

    /**
     * Get the order value of this object.
     * <p>Higher values are interpreted as lower priority. As a consequence,
     * the object with the lowest value has the highest priority (somewhat
     * analogous to Servlet {@code load-on-startup} values).
     * <p>Same order values will result in arbitrary sort positions for the
     * affected objects.
     *
     * @return the order value
     * @see #HIGHEST_PRECEDENCE
     * @see #LOWEST_PRECEDENCE
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
