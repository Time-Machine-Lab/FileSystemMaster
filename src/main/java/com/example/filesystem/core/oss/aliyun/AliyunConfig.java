package com.example.filesystem.core.oss.aliyun;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author welsir
 * @Date 2023/11/30 20:01
 */
@Data
@Configuration
@Component
public class AliyunConfig {
    @Value("${aliyun.oss.endpoint}")
    private String[] endpoints;
    @Value("${aliyun.oss.accessKeyId}")
    private String accessKeyId;
    @Value("${aliyun.oss.accessKeySecret}")
    private String accessKeySecret;
    @Value("${aliyun.oss.bucket}")
    private String[] buckets;
}
