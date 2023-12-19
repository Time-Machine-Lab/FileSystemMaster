package com.example.filesystem.core.local;

import com.example.filesystem.common.AbstractAssert;
import com.example.filesystem.common.Result;
import com.example.filesystem.common.log.AbstractLogger;
import com.example.filesystem.core.strategy.FileStrategy;
import com.example.filesystem.mapper.FileMapper;
import com.example.filesystem.pojo.SingleFile;
import com.example.filesystem.pojo.StatusConstEnum;
import com.example.filesystem.pojo.vo.CommonDownloadVO;
import com.example.filesystem.pojo.vo.CommonFileVO;
import com.example.filesystem.pojo.vo.UploadFileVO;
import com.example.filesystem.util.FileUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * @Description
 * @Author welsir
 * @Date 2023/11/30 19:53
 */
@Component
public class LocalFileStrategy extends FileStrategy {

    @Resource
    FileUtils fileUtils;
    @Resource
    AbstractLogger logger;
    @Resource
    FileMapper fileMapper;
    @Override
    public <T extends CommonFileVO> String download(T commonFileVO) {
        SingleFile singleFile = fileMapper.selectById(commonFileVO.getFileId());
        AbstractAssert.isNull(singleFile, StatusConstEnum.FILE_NOT_EXIT);
        String fileType = singleFile.getOriginName().split("\\.")[1];
        return singleFile.getPath()+"/"+singleFile.getMd5()+"."+fileType;
    }

    @Override
    public <T extends CommonFileVO> UploadFileVO upload(T commonFileVO) {
        String filename = commonFileVO.getFile().getOriginalFilename();
        fileUtils.createFolderIfAbenset(commonFileVO.getPath());
        File loacalFile = new File(commonFileVO.getPath() + filename);
        try {
            SingleFile file = SingleFile.builder()
                    .md5(commonFileVO.getMd5())
                    .path(commonFileVO.getPath())
                    .originName(commonFileVO.getFile().getOriginalFilename()).build();
            commonFileVO.getFile().transferTo(loacalFile);
            fileMapper.insert(file);
            return UploadFileVO.builder()
                    .fileId(String.valueOf(file.getId()))
                    .url(loacalFile.getPath())
                    .build();
        } catch (IOException e) {
            logger.error(e.toString(),e);
        }
        return null;
    }
}
