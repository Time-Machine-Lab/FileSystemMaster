package com.example.filesystem.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
    SYSTEM_ERROR(500, "系统异常");

    /**
     * 状态码
     */
    private final Integer code;

    /**
     * 描述
     */
    private final String desc;
}
