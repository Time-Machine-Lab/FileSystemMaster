package com.example.filesystem.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * @Description
 * @Author welsir
 * @Date 2023/11/30 18:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OSSFileVO extends CommonFileVO{

    @NotBlank(message = "bucket不能为空")
    private String bucket;
}
