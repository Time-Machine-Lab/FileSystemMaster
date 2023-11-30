package com.example.filesystem.core.local;

import com.example.filesystem.common.Result;
import com.example.filesystem.common.log.AbstractLogger;
import com.example.filesystem.core.strategy.FileStrategy;
import com.example.filesystem.mapper.FileMapper;
import com.example.filesystem.pojo.SingleFile;
import com.example.filesystem.pojo.vo.CommonDownloadVO;
import com.example.filesystem.pojo.vo.CommonFileVO;
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
        String fileType = singleFile.getOriginName().split("\\.")[1];
        return singleFile.getPath()+singleFile.getMd5()+"."+fileType;
    }

    @Override
    public <T extends CommonFileVO> String upload(T commonFileVO) {
        String filename = commonFileVO.getFile().getOriginalFilename();
        String size = String.valueOf(commonFileVO.getFile().getSize());
        fileUtils.createFolderIfAbenset(commonFileVO.getSavePath());
        File loacalFile = new File(commonFileVO.getSavePath() + filename);
        try {
            commonFileVO.getFile().transferTo(loacalFile);
            Date date = new Date(System.currentTimeMillis());
            return String.valueOf(date.toString());
        } catch (IOException e) {
            logger.error(e.toString(),e);
        }
        return null;
    }
}
