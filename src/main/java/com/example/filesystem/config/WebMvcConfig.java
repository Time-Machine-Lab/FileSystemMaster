package com.example.filesystem.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Description
 * @Author welsir
 * @Date 2023/12/20 22:03
 */

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Value("${file.uploadUrl}")
    private String fileUrl;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("file:"+fileUrl);
    }

}
