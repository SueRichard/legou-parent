package com.hh.auth.interceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hh.auth.utils.AdminToken;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

/**
 * feign 拦截器
 * 调用feign之前执行
 * RBAC角色权限控制（security-service） 开启了资源服务器的授权认证访问，需要传递令牌
 * 传递管理员令牌，由于是微服务内部之间的调用，所以使用管理员令牌
 *
 * @author hh
 * @version 1.0
 * @time 23/12/2023 17:00
 */
@Component
public class TokenRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        String token = null;
        try {
            token = AdminToken.adminToken();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        template.header("Authorization", "Bearer " + token);
    }
}
