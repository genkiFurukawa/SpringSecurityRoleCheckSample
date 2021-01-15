package com.example.demo.config;

import com.example.demo.component.CustomHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    CustomHandlerInterceptor customHandlerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(customHandlerInterceptor)
                .addPathPatterns("/**") // 適用対象のパス(パターン)を指定する
                .excludePathPatterns("/static/**"); // 除外するパス(パターン)を指定する
    }
}
