package com.example.filesystem.core.oss.aliyun;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.example.filesystem.common.AbstractAssert;
import com.example.filesystem.common.BaseException;
import com.example.filesystem.common.constant.CommonConstant;
import com.example.filesystem.common.log.AbstractLogger;
import com.example.filesystem.config.SystemConfig;
import com.example.filesystem.core.oss.OSSFileOperatorInterface;
import com.example.filesystem.mapper.FileBucketMapper;
import com.example.filesystem.mapper.FileMapper;
import com.example.filesystem.pojo.FileBucket;
import com.example.filesystem.pojo.SingleFile;
import com.example.filesystem.pojo.StatusConstEnum;
import com.example.filesystem.pojo.vo.DownloadFileVO;
import com.example.filesystem.pojo.vo.OSSFileVO;
import com.example.filesystem.pojo.vo.UploadFileVO;
import com.example.filesystem.util.FileUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

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
    @Resource
    FileBucketMapper fileBucketMapper;
    @Resource
    private TransactionTemplate transactionTemplate;
    @Override
    public UploadFileVO uploadFile(OSSFileVO ossFileVO) {
        String path = ossFileVO.getPath().replace("\\","/");
        String fileType = FileUtils.getExtension(Objects.requireNonNull(ossFileVO.getFile().getOriginalFilename()));
        String[] endpoints = aliyunConfig.getEndpoints();
        String[] buckets = aliyunConfig.getBuckets();
        int bucketIndex = getBucketIndex(ossFileVO.getBucket());
        String uploadBucket;
        String uploadEndpoint;
        if(bucketIndex!=-1){
            uploadEndpoint = endpoints[bucketIndex];
            uploadBucket = buckets[bucketIndex];
        }else {
            throw new BaseException(StatusConstEnum.QUERY_BUCKET_ERROR);
        }
        String fileName;
        if(!StringUtils.isBlank(ossFileVO.getPath())){
            fileName = path +"/" + ossFileVO.getMd5()+ "." + fileType;
        }else {
            fileName =  ossFileVO.getMd5()+ "." + fileType;
            fileName = fileName.substring(1);
        }
        String uploadPath = fileName;
        SingleFile singleFile = new SingleFile();
        singleFile.setMd5(ossFileVO.getMd5());
        singleFile.setPath(ossFileVO.getPath());
        singleFile.setOriginName(ossFileVO.getFile().getOriginalFilename());
        String accessKeyId =aliyunConfig.getAccessKeyId();
        String accessKeySecret = aliyunConfig.getAccessKeySecret();
        transactionTemplate.execute(transactionStatus ->{
            logger.info("上传文件名:%s,文件大小:%s",ossFileVO.getFile().getOriginalFilename(),ossFileVO.getFile().getSize());
            OSSClient ossClient = new OSSClient(uploadEndpoint, accessKeyId, accessKeySecret);
            try {
                ossClient.putObject(uploadBucket,uploadPath, ossFileVO.getFile().getInputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            fileMapper.insert(singleFile);
            fileBucketMapper.insertFileBucketRelative(singleFile.getId().toString(),uploadBucket, CommonConstant.ALIYUN_OSS);
            return Boolean.TRUE;
        });
        return UploadFileVO.builder()
                .fileId(String.valueOf(singleFile.getId()))
                .url("https://" + uploadBucket + "." + uploadEndpoint +"/"+uploadPath)
                .build();
    }

    @Override
    public String downloadFile(DownloadFileVO ossFileVO) {

        AbstractAssert.isNull(fileBucketMapper.queryFileIsExit(ossFileVO.getBucket(),ossFileVO.getFileId()),StatusConstEnum.FILE_NOT_EXIT);

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
            String fileType = singleFile.getOriginName().split("\\.")[1];
            fileName = singleFile.getPath()+"/"+fileName+"."+fileType;
            if(bucketIsPrivate(bucket)){
                Date expiration = new Date(new Date().getTime() + 3600 * 1000); // 1小时后过期
                GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucket,fileName);
                request.setExpiration(expiration);
                return ossClient.generatePresignedUrl(request).toString();
            }else {
                return "https://"+bucket+"."+endpoint+"/"+fileName;
            }
        }catch (RuntimeException e){
            logger.error("%s:"+e.getStackTrace()[0],e);
            throw new BaseException(StatusConstEnum.OSS_FILE_DOWNLOAD_ERROR);
        } finally {
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

    private boolean bucketIsPrivate(String bucket){
        String[] isPrivate = aliyunConfig.getBucketPrivate();
        return "true".equals(isPrivate[getBucketIndex(bucket)]);
    }

}
