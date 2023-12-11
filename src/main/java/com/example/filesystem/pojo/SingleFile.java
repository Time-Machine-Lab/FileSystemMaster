package com.example.filesystem.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author welsir 单个文件
 * @Date 2023/11/30 20:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("rvc_file_system")
public class SingleFile {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String md5;
    @TableField(value = "origin_name")
    private String originName;
    private String path;
}
