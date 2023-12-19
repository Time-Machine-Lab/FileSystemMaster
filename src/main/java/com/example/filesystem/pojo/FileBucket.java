package com.example.filesystem.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author welsir
 * @Date 2023/12/1 20:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("rvc_file_bucket")
public class FileBucket {

    private String id;
    private String bucket;

}
