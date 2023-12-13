package com.example.filesystem.pojo.vo;

import lombok.Data;

/**
 * @Description
 * @Author welsir
 * @Date 2023/12/13 14:17
 */
@Data
public class DownloadFileVO {

    private String fileId;
    private String bucket;
    private String isPrivate;
}
