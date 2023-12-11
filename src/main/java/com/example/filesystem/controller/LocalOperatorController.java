package com.example.filesystem.controller;

import com.example.filesystem.common.Result;
import com.example.filesystem.core.local.LocalFileStrategy;
import com.example.filesystem.pojo.vo.CommonFileVO;

import com.example.filesystem.pojo.vo.UploadFileVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description
 * @Author welsir
 * @Date 2023/11/30 18:44
 */
@RequestMapping("file/local")
@RestController
public class LocalOperatorController  {

    @Resource
    LocalFileStrategy strategy;

    @PostMapping("/upload")
    public Result<?> upload(CommonFileVO commonFileVO){
        UploadFileVO res = strategy.upload(commonFileVO);
        return Result.success(res);
    }

    @PostMapping("/download")
    public Result<?> download(CommonFileVO commonFileVO){
        String res = strategy.download(commonFileVO);
        return Result.success(res);
    }

}
