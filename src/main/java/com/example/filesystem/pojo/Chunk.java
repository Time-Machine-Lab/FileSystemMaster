package com.example.filesystem.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description
 * @Author welsir
 * @Date 2023/11/24 19:40
 */


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Chunk {

    /*
     * 文件总md5
     */
    private String md5;

    /*
     * 分块文件
     */
    private MultipartFile chunkFile;

    /*
     * 分块序号
     */
    private Integer chunkNumber;

    /*
     * 分块总数
     */
    private Integer totalChunks;

    /*
     * 目前块大小
     */
    private Long currentChunkSize;

    /*
     * 文件总大小
     */
    private Long allFileSize;

    /*
     * 文件存储根路径
     */
    private String filePath;

    /*
     * 文件名称
     */
    private String filename;

}
