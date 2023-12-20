package com.example.filesystem.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.filesystem.pojo.FileBucket;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * @Description
 * @Author welsir
 * @Date 2023/12/1 20:26
 */
public interface FileBucketMapper extends BaseMapper<FileBucket> {

    @Select("select ffb.* from rvc_file_file_bucket ffb join rvc_file_bucket fb on ffb.bucket_id = fb.id where ffb.file_id = #{fileId} and fb.bucket = #{bucket}")
    Object queryFileIsExit(String bucket,String fileId);

    @Insert("INSERT INTO rvc_file_file_bucket (bucket_id, file_id)\n" +
            "SELECT \n" +
            "    (SELECT id FROM rvc_file_bucket WHERE bucket = #{bucketName} AND type = #{type}),\n" +
            "    #{fileId}")
    int insertFileBucketRelative(String fileId,String bucketName,String type);

    @Select("select id from rvc_file_bucket where bucket = #{bucketName} and type = #{type}")
    String queryBucketId(String bucketName,String type);
}
