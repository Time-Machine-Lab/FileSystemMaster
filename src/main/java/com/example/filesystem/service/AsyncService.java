package com.example.filesystem.service;

import com.aliyun.oss.OSSClient;
import com.example.filesystem.common.BaseException;
import com.example.filesystem.core.oss.aliyun.AliyunConfig;
import com.example.filesystem.pojo.StatusConstEnum;
import com.example.filesystem.pojo.vo.OSSFileVO;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * @Description
 * @Author welsir
 * @Date 2023/12/20 11:47
 */
@Component
public class AsyncService {

    @Resource
    AliyunConfig aliyunConfig;
    @Async
    public void asyncUpload(OSSFileVO ossFileVO,String uploadEndpoint,String uploadBucket,InputStream fileInputStream){
        long uploadTime = new Date(System.currentTimeMillis()).getTime();
        String accessKeyId =aliyunConfig.getAccessKeyId();
        String accessKeySecret = aliyunConfig.getAccessKeySecret();
        String fileName = ossFileVO.getMd5();
        String fileType = ossFileVO.getFile().getOriginalFilename().split("\\.")[1];
        fileName = ossFileVO.getPath()+"/"+fileName+"."+fileType;
        OSSClient ossClient = new OSSClient(uploadEndpoint, accessKeyId, accessKeySecret);
        ossClient.putObject(uploadBucket,fileName, fileInputStream);
        System.out.println("upload:"+(new Date(System.currentTimeMillis()).getTime()-uploadTime));
        try {
            fileInputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
