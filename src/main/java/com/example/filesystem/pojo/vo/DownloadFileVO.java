package com.example.filesystem.pojo.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Description
 * @Author welsir
 * @Date 2023/12/13 14:17
 */
@Data
public class DownloadFileVO {

    @NotBlank(message = "文件id不能为空")
    private String fileId;
    @NotBlank(message = "bucket不能为空")
    private String bucket;
}
