package com.example.filesystem.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author welsir
 * @Date 2023/11/24 20:43
 */
@Getter
@AllArgsConstructor
public enum StatusConstEnum {
    /**
     * 成功
     */
    SUCCESS(200, "成功"),

    /**
     * 系统异常
     */
    SYSTEM_ERROR(500, "系统异常"),

    /**
     * OSS文件上传失败
     */
    OSS_FILE_UPLOAD_ERROR(501,"文件上传至OSS失败"),
    /**
     * OSS文件上传失败
     */
    OSS_FILE_DOWNLOAD_ERROR(502,"文件下载失败"),
    /**
     * 获取bucket错误
     */
    QUERY_BUCKET_ERROR(512,"bucket获取失败"),

    FILE_IS_NULL(513,"上传文件为空"),

    FILE_PATH_ERROR(514,"文件路径不正确"),

    FILE_MD5_ERROR(515,"文件MD5不能为空"),
    FILE_NOT_EXIT(516,"文件不存在");



    /**
     * 状态码
     */
    private final Integer code;

    /**
     * 描述
     */
    private final String desc;
}
