package com.example.rvc_filesystem.controller;

import com.example.rvc_filesystem.common.log.AbstractLogger;
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

    /*
     * 普通文件上传
     */
    @PostMapping("/upload")
    @ResponseBody
    public Boolean upload(@RequestParam("file")MultipartFile file,@RequestParam("path")String savePath){
        String filename = file.getOriginalFilename();
        String size = String.valueOf(file.getSize());
        File loacalFile = new File(savePath + filename);
        try {
            file.transferTo(loacalFile);
            Date date = new Date(System.currentTimeMillis());
            logger.info("filename {}",filename);
            logger.info("size {}",size);
            logger.info("date {}",date);
            return true;
        } catch (IOException e) {
            logger.error(e.toString(),e);
        }
        return false;
    }

    /*
     * 分片上传
     */
    @PostMapping("/splitUpload")
    @ResponseBody
    public Boolean upload(@RequestParam("file")MultipartFile file,long splitSize,String savePath){

        String fineName = file.getOriginalFilename();
        String fullPath = savePath+fineName;
        File localFile = new File(fullPath);
        try {
            file.transferTo(localFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

}
