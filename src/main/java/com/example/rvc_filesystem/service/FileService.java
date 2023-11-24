package com.example.rvc_filesystem.service;

import com.example.rvc_filesystem.common.log.AbstractLogger;
import com.example.rvc_filesystem.pojo.Chunk;
import com.example.rvc_filesystem.util.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * @Description
 * @Author welsir
 * @Date 2023/11/24 20:08
 */
@Service
public class FileService {

    @Resource
    FileUtils fileUtils;
    @Resource
    AbstractLogger logger;


    public void uploadChunk(Chunk chunk){

        String fileFolderPath = chunk.getFilePath()+File.separator+fileUtils.getFileFolderPath(chunk.getMd5());

        File folderFile = fileUtils.createFolderIfAbenset(fileFolderPath);

        String writePath = folderFile.getAbsolutePath()+File.separator+chunk.getFilename()+"."+chunk.getChunkNumber();
        try (
                InputStream in = chunk.getChunkFile().getInputStream();
                FileOutputStream os = new FileOutputStream(
                        writePath)
        ) {
            IOUtils.copy(in, os);
            logger.info("文件标识:%s, chunkNumber:%s", chunk.getMd5(), chunk.getChunkNumber());
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }
}
