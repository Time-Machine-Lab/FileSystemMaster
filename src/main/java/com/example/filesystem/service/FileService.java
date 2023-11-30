package com.example.filesystem.service;

import com.example.filesystem.common.log.AbstractLogger;
import com.example.filesystem.pojo.vo.ChunkVO;
import com.example.filesystem.util.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

    private Map<String,String> fileMap = new ConcurrentHashMap<>();

    public Map chunkUpload(ChunkVO chunk){
        Map<String, String> map = new HashMap<>();
        String fileFolderPath = chunk.getFilePath()+File.separator+fileUtils.getFileFolderPath(chunk.getMd5());

        File folderFile = fileUtils.createFolderIfAbenset(fileFolderPath);

        String writePath = folderFile.getAbsolutePath()+File.separator+chunk.getFilename()+"."+chunk.getChunkNumber();
        try (
                InputStream in = chunk.getChunkFile().getInputStream();
                FileOutputStream os = new FileOutputStream(
                        writePath)
        ) {
            IOUtils.copy(in, os);
            String chunkFile = chunk.getFilename()+"."+chunk.getChunkNumber();
            fileMap.put(chunkFile,chunk.getMd5());
            logger.info("文件标识:%s, chunkNumber:%s", chunk.getMd5(), chunk.getChunkNumber());
            if(chunk.getChunkNumber().equals(chunk.getTotalChunks())){
                logger.info("文件分片上传完成,总文件大小为:%s",chunk.getAllFileSize());
                map.put("Date",new Date(System.currentTimeMillis()).toString());
                map.put("chunkNumber",chunk.getChunkNumber().toString());
                map.put("isFinish","true");
                Integer mb = fileUtils.translateByteToMB(chunk.getAllFileSize());
                map.put("fileSize",(mb).toString());
            }else{
                map.put("Date",new Date(System.currentTimeMillis()).toString());
                map.put("chunkNumber",chunk.getChunkNumber().toString());
                map.put("isFinish","false");
            }
            return map;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return map;
    }

    private Boolean verifyContentHash(String md5){
        return fileMap.containsKey(md5);

    }
}
