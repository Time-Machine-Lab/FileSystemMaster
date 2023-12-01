package com.example.filesystem.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.filesystem.pojo.FileBucket;

/**
 * @Description
 * @Author welsir
 * @Date 2023/12/1 20:26
 */
@TableName("rvc_file_bucket")
public interface FileBucketMapper extends BaseMapper<FileBucket> {

}
