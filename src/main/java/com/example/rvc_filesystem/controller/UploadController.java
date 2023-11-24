package com.example.rvc_filesystem.controller;

import com.example.rvc_filesystem.common.Result;
import com.example.rvc_filesystem.common.log.AbstractLogger;
import com.example.rvc_filesystem.pojo.vo.ChunkVO;
import com.example.rvc_filesystem.pojo.vo.CommonFileVO;
import com.example.rvc_filesystem.service.FileService;
import com.example.rvc_filesystem.util.FileUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

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
    public Result<?> upload(CommonFileVO commonFileVO){
        String filename = commonFileVO.getFile().getOriginalFilename();
        String size = String.valueOf(commonFileVO.getFile().getSize());
        fileUtils.createFolderIfAbenset(commonFileVO.getSavePath());
        File loacalFile = new File(commonFileVO.getSavePath() + filename);
        try {
            commonFileVO.getFile().transferTo(loacalFile);
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
    public Result<?> upload(ChunkVO chunk){
        Map map = fileService.chunkUpload(chunk);
        return Result.success(map);
    }

}
