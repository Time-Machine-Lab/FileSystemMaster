package com.example.filesystem.controller;

import com.example.filesystem.common.Result;
import com.example.filesystem.core.strategy.FileStrategy;
import com.example.filesystem.pojo.vo.CommonDownloadVO;
import com.example.filesystem.pojo.vo.CommonFileVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description
 * @Author welsir
 * @Date 2023/11/30 14:51
 */
@RestController
@RequestMapping("/file")
public abstract class FileController<T extends CommonFileVO,S extends FileStrategy> {

    @Resource
    S s;

    @PostMapping("/upload")
    public Result<?> upload(T commonFileVO){
        String res = s.upload(commonFileVO);
        return Result.success(res);
    }

    @PostMapping("/download")
    public Result<?> download(T commonFileVO){
        String res = s.download(commonFileVO);
        return Result.success(res);
    }

}
