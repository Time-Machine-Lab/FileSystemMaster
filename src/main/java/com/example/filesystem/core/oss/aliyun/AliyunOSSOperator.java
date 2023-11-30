package com.example.filesystem.core.oss.aliyun;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.example.filesystem.common.BaseException;
import com.example.filesystem.common.log.AbstractLogger;
import com.example.filesystem.core.oss.OSSFileOperatorInterface;
import com.example.filesystem.core.strategy.FileStrategy;
import com.example.filesystem.mapper.FileMapper;
import com.example.filesystem.pojo.SingleFile;
import com.example.filesystem.pojo.StatusConstEnum;
import com.example.filesystem.pojo.vo.OSSFileDownloadVO;
import com.example.filesystem.pojo.vo.OSSFileVO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * @Description
 * @Author welsir
 * @Date 2023/11/30 15:17
 */
@Component("aliyun")
public class AliyunOSSOperator implements OSSFileOperatorInterface {

    @Resource
    AbstractLogger logger;
    @Resource
    AliyunConfig aliyunConfig;
    @Resource
    FileMapper fileMapper;
    @Override
    public String uploadFile(OSSFileVO ossFileVO) {
        String[] endpoints = aliyunConfig.getEndpoints();
        String accessKeyId =aliyunConfig.getAccessKeyId();
        String accessKeySecret = aliyunConfig.getAccessKeySecret();
        String[] buckets = aliyunConfig.getBuckets();
        String uploadEndpoint;
        String uploadBucket;
        int bucketIndex = getBucketIndex(ossFileVO.getBucket());
        if(bucketIndex!=-1){
            uploadEndpoint = endpoints[bucketIndex];
            uploadBucket = buckets[bucketIndex];
        }else {
            throw new BaseException(StatusConstEnum.QUERY_BUCKET_ERROR);
        }
        try {
            OSSClient ossClient = new OSSClient(uploadEndpoint, accessKeyId, accessKeySecret);
            InputStream inputStream = ossFileVO.getFile().getInputStream();
            String fileName = ossFileVO.getMd5();
            fileName = ossFileVO.getSavePath()+"/"+fileName;
            ossClient.putObject(uploadBucket,fileName,inputStream);
            SingleFile singleFile = new SingleFile();
            singleFile.setMd5(ossFileVO.getMd5());
            singleFile.setPath(ossFileVO.getSavePath());
            singleFile.setOriginName(ossFileVO.getFile().getOriginalFilename());
            fileMapper.insert(singleFile);
            return "https://"+uploadBucket+"."+uploadEndpoint+"/"+fileName;
        } catch (IOException e) {
            logger.debug(e.getMessage(),e);
            throw new BaseException(StatusConstEnum.OSS_FILE_UPLOAD_ERROR);
        }
    }

    @Override
    public String downloadFile(OSSFileDownloadVO ossFileVO) {

        String accessKeyId = aliyunConfig.getAccessKeyId();
        String accessKeySecret = aliyunConfig.getAccessKeySecret();
        int bucketIndex = getBucketIndex(ossFileVO.getBucket());
        String endpoint;
        String bucket;
        if(bucketIndex!=-1){
            endpoint = aliyunConfig.getEndpoints()[bucketIndex];
            bucket = aliyunConfig.getBuckets()[bucketIndex];
        }else {
            throw new BaseException(StatusConstEnum.QUERY_BUCKET_ERROR);
        }
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            SingleFile singleFile = fileMapper.selectById(ossFileVO.getFileId());
            String fileName = singleFile.getMd5();
            fileName = singleFile.getPath()+fileName;
            if(ossFileVO.isPrivate()){
                Date expiration = new Date(new Date().getTime() + 3600 * 1000); // 1小时后过期
                GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucket,fileName);
                request.setExpiration(expiration);
                return ossClient.generatePresignedUrl(request).toString();
            }else {
                return "https://"+bucket+"."+endpoint+"/"+fileName;
            }
        }finally {
            ossClient.shutdown();
        }
    }

    private int getBucketIndex(String bucket){
        String[] buckets = aliyunConfig.getBuckets();
        for (int i = 0; i < buckets.length; i++) {
            if(buckets[i].equals(bucket)){
                return i;
            }
        }
        return -1;
    }

}
