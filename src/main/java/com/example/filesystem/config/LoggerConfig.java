package com.example.filesystem.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author welsir
 * @Date 2023/11/23 19:06
 */
@Data
@Configuration
@Component
public class LoggerConfig {

    @Value("${file.logger.handler}")
    private String logger;

    @Value("${file.logger.enable}")
    private boolean enable;

}
