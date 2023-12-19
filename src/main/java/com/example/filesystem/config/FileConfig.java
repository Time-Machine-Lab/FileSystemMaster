package com.example.filesystem.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description
 * @Author welsir
 * @Date 2023/12/19 15:55
 */
@Configuration
public class FileConfig implements WebMvcConfigurer {

    @Value("${file.uploadUrl}")
    private String  fileUrl;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry register){
        register.addResourceHandler("/images/**").addResourceLocations("file:"+fileUrl);
    }

}
