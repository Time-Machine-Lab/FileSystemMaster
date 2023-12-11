package com.example.filesystem.pojo.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @Description
 * @Author welsir
 * @Date 2023/12/11 22:38
 */
@Data
@Builder
public class UploadFileVO {

    private String fileId;
    private String url;

}
