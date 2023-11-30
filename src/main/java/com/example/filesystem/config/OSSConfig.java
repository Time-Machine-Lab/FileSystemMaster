package com.example.filesystem.config;

import lombok.Data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author welsir
 * @Date 2023/11/30 15:14
 */
@Data
@Configuration
public class OSSConfig {

    @Value("${oss.handler}")
    private String oss;

}
