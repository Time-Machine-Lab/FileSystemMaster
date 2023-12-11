package com.example.filesystem.controller;

import com.example.filesystem.common.Result;
import com.example.filesystem.core.oss.AbstractOSSFileStrategy;
import com.example.filesystem.pojo.vo.OSSFileVO;
import com.example.filesystem.pojo.vo.UploadFileVO;
import org.apache.tomcat.jni.OS;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description
 * @Author welsir
 * @Date 2023/11/30 17:39
 */
@RequestMapping("/file/oss")
@RestController
public class OSSOperatorController {

    @Resource
    AbstractOSSFileStrategy strategy;

    @PostMapping("/upload")
    public Result<?> upload(OSSFileVO commonFileVO){
        UploadFileVO res = strategy.upload(commonFileVO);
        return Result.success(res);
    }

    @PostMapping("/download")
    public Result<?> download(OSSFileVO commonFileVO){
        String res = strategy.download(commonFileVO);
        return Result.success(res);
    }

}
