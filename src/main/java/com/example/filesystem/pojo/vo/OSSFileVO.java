package com.example.filesystem.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author welsir
 * @Date 2023/11/30 18:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OSSFileVO extends CommonFileVO{

    private String bucket;
    private String isPrivate;
}
