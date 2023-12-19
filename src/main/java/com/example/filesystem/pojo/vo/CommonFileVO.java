package com.example.filesystem.pojo.vo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Description
 * @Author welsir
 * @Date 2023/11/24 22:44
 */
@Data
public class CommonFileVO {
    private String fileId;
    @NotNull(message = "文件不能为空")
    private MultipartFile file;
    private String path;
    @NotBlank(message = "文件md5不能为空")
    private String md5;
}
