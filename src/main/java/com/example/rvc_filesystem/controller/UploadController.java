package com.example.rvc_filesystem.controller;

import com.example.rvc_filesystem.common.Result;
import com.example.rvc_filesystem.common.log.AbstractLogger;
import com.example.rvc_filesystem.pojo.Chunk;
import com.example.rvc_filesystem.service.FileService;
import com.example.rvc_filesystem.util.FileUtils;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.util.Date;

/**
 * @Description fileUploadController类
 * @Author welsir
 * @Date 2023/11/23 18:15
 */
@RestController
@RequestMapping("/file")
public class UploadController {

    @Resource
    AbstractLogger logger;
    @Resource
    FileUtils fileUtils;
    @Resource
    FileService fileService;

    /*
     * 普通文件上传
     */
    @PostMapping("/upload")
    @ResponseBody
    public Result<?> upload(@RequestBody MultipartFile file,@RequestParam("path")String savePath){
        String filename = file.getOriginalFilename();
        String size = String.valueOf(file.getSize());
        fileUtils.createFolderIfAbenset(savePath);
        File loacalFile = new File(savePath + filename);
        try {
            file.transferTo(loacalFile);
            Date date = new Date(System.currentTimeMillis());
            logger.info("filename %s",filename);
            logger.info("size %s",size);
            logger.info("date %s",date);
            return Result.success();
        } catch (IOException e) {
            logger.error(e.toString(),e);
        }
        return Result.fail("文件上传失败!");
    }

    /**
     * @description 分片上传
     * @param chunk
     * @return Boolean
     */
    @PostMapping("/chunkUpload")
    @ResponseBody
    public Result<?> upload(Chunk chunk){
        fileService.uploadChunk(chunk);
        return Result.success();
    }

}
