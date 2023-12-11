package com.example.filesystem.pojo.vo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description
 * @Author welsir
 * @Date 2023/11/24 22:44
 */
@Data
public class CommonFileVO {
    private String fileId;
    private MultipartFile file;
    private String path;
    private String md5;
}
