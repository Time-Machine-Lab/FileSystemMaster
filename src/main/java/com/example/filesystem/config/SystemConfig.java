package com.example.filesystem.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author welsir
 * @Date 2023/12/19 15:51
 */
@Configuration
@Data
public class SystemConfig {

    @Value("${file.uploadUrl}")
    private String uploadUrl;

    @Value("${file.domain}")
    private String domain;
}
