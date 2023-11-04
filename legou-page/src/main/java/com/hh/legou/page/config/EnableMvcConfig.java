package com.hh.legou.page.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author hh
 * @version 1.0
 * @time 04/11/2023 09:59
 */
//前置controller中执行
@ControllerAdvice
@Configuration
public class EnableMvcConfig implements WebMvcConfigurer {
    //WebMvcConfigurer：mvc在springboot中的自定义配置

    /**
     * 静态资源放行访问
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //注意路径，注意路径最后的斜杆items/
        registry.addResourceHandler("/items/**").addResourceLocations("classpath:/templates/items/");
    }
}
