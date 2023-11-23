package com.example.rvc_filesystem.config;

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
@ConfigurationProperties(prefix = "system")
public class LoggerConfig {

    @Value("${file.logger.handler}")
    private String Logger;

    @Value("${file.logger.enable}")
    private boolean enable;

}
