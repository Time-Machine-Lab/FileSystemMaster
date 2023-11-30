package com.example.filesystem.pojo.vo;

import lombok.Data;

/**
 * @Description
 * @Author welsir
 * @Date 2023/11/30 20:54
 */
@Data
public class OSSFileDownloadVO extends CommonFileVO{

    private String bucket;
    private boolean isPrivate;

}
