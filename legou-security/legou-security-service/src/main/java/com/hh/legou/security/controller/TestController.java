package com.hh.legou.security.controller;

import org.apache.ibatis.javassist.compiler.ast.StringL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author hh
 * @version 1.0
 * @time 21/12/2023 15:56
 */
@RestController
@RequestMapping("/hh")
public class TestController {
    public static final Logger log = LoggerFactory.getLogger(TestController.class);

    @GetMapping("/product/{id}")
    public String getProduct(@PathVariable String id) {
        return "product: " + id;
    }

    @GetMapping("/order/{id}")
    public String getOrder(@PathVariable String id) {
        return "order: " + id;
    }

    @GetMapping("/book/{id}")
    public String getBook(@PathVariable String id) {
        return "book id :" + id;
    }

    /**
     * 获取认证用户信息
     *
     * @param oAuth2Authentication
     * @param principal
     * @param authentication
     * @return
     */
    @GetMapping("/getPrincipal")
    public OAuth2Authentication getPrincipal(OAuth2Authentication oAuth2Authentication, Principal principal, Authentication authentication) {
        //认证权限
        log.info(oAuth2Authentication.getUserAuthentication().getAuthorities().toString());
        log.info(oAuth2Authentication.toString());
        log.info("principal.toString() " + principal.toString());
        //用户名
        log.info("principal.getName() " + principal.getName());
        //认证权限
        log.info("authentication: " + authentication.getAuthorities().toString());
        return oAuth2Authentication;
    }
}
