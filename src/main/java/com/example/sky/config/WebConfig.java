package com.example.sky.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    /**
     * 配置静态资源访问
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 配置图片访问路径
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:uploads/images/");
        
        // 配置缩略图访问路径
        registry.addResourceHandler("/thumbnails/**")
                .addResourceLocations("file:uploads/images/thumbnails/");
    }
} 