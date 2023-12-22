package com.hh.cloud.filter;

import com.hh.cloud.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
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
 * @author hh
 * @version 1.0
 * @time 09/11/2023 08:20
 */
//@Component
public class AuthorizeFilter implements GlobalFilter, Ordered {
    private static final String AUTHORIZE_TOKEN = "Authorization";

    /**
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        //登录放行
        if (request.getURI().getPath().startsWith("/user/login")) {
            return chain.filter(exchange);
        }
        //头部获得令牌
        String token = request.getHeaders().getFirst(AUTHORIZE_TOKEN);
        if (StringUtils.isEmpty(token)) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        //校验令牌
        try {
            Claims claims = JwtUtil.parseJWT(token);
            //解析用户角色，针对不同角色访问对应的微服务
        } catch (Exception e) {
            e.printStackTrace();
            //解析失败
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        //能够解析，放行
        return chain.filter(exchange);
    }

    /**
     * @return
     */
    @Override
    public int getOrder() {
        //值越小，在过滤器链中优先级越高
        return 0;
    }
}
