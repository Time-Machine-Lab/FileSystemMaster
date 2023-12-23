package com.example.filesystem.controller;

import com.example.filesystem.common.Result;
import com.example.filesystem.common.log.AbstractLogger;
import com.example.filesystem.core.oss.AbstractOSSFileStrategy;
import com.example.filesystem.pojo.vo.DownloadFileVO;
import com.example.filesystem.pojo.vo.OSSFileVO;
import com.example.filesystem.pojo.vo.UploadFileVO;
import com.example.filesystem.util.ConcurrentUtil;
import org.apache.tomcat.jni.OS;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Description 111
 * @Author welsir
 * @Date 2023/11/30 17:39
 */
@RequestMapping("/file/oss")
@RestController
public class OSSOperatorController {

    @Resource
    AbstractOSSFileStrategy strategy;
    @Resource
    AbstractLogger logger;

    private static final ExecutorService executorService = Executors.newFixedThreadPool(50);

    @PostMapping("/upload")
    public Result<?> upload(@Validated OSSFileVO commonFileVO){
        UploadFileVO res = strategy.upload(commonFileVO);
        return Result.success(res);
    }

    @PostMapping("/download")
    public Result<?> download(@Validated DownloadFileVO commonFileVO){
        String res = strategy.download(commonFileVO);
        return Result.success(res);
    }

    @PostMapping("/upload/list")
    public Result<?> upload(@RequestParam("file") MultipartFile[] files,
                            @RequestParam("md5List") List<String> md5List,
                            @RequestParam("pathList") List<String> pathList,
                            @RequestParam("bucket") String bucket){
        ArrayList<UploadFileVO> res = new ArrayList<>();
        OSSFileVO ossFileVO = new OSSFileVO();
        logger.info("文件信息:%s,%s,%s,%s",files.length, md5List.get(0), pathList.get(0),bucket);
        ossFileVO.setFile(files[0]);
        ossFileVO.setPath(pathList.get(0));
        ossFileVO.setMd5(md5List.get(0));
        ossFileVO.setBucket(bucket);
        Callable<UploadFileVO> file1 = ()->strategy.upload(ossFileVO);
        ossFileVO.setFile(files[1]);
        ossFileVO.setPath(pathList.get(0));
        ossFileVO.setMd5(md5List.get(0));
        Callable<UploadFileVO> file2 = ()->strategy.upload(ossFileVO);

        Future<UploadFileVO> res1 = ConcurrentUtil.doJob(executorService, file1);
        Future<UploadFileVO> res2 = ConcurrentUtil.doJob(executorService, file2);

        res.add(ConcurrentUtil.futureGet(res1));
        res.add(ConcurrentUtil.futureGet(res2));
        return Result.success(res);
    }
}
