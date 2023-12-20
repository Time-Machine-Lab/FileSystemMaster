package com.example.filesystem.core.local;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.filesystem.common.AbstractAssert;
import com.example.filesystem.common.Result;
import com.example.filesystem.common.log.AbstractLogger;
import com.example.filesystem.config.SystemConfig;
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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @Description
 * @Author welsir
 * @Date 2023/11/30 19:53
 */
@Component
public class LocalFileStrategy extends FileStrategy {

    @Resource
    AbstractLogger logger;
    @Resource
    FileMapper fileMapper;
    @Resource
    SystemConfig systemConfig;
    @Override
    public <T extends CommonFileVO> String download(T commonFileVO) {
        SingleFile singleFile = fileMapper.selectById(commonFileVO.getFileId());
        AbstractAssert.isNull(singleFile, StatusConstEnum.FILE_NOT_EXIT);
        String fileType = singleFile.getOriginName().split("\\.")[1];
        return "www."+systemConfig.getDomain()+singleFile.getPath()+"/"+singleFile.getMd5()+"."+fileType;
    }

    @Override
    public <T extends CommonFileVO> UploadFileVO upload(T commonFileVO) {
        String saveFolder = systemConfig.getUploadUrl().replace("\\","/");
        String fileType = FileUtils.getExtension(commonFileVO.getFile().getOriginalFilename());
        String prePath = commonFileVO.getPath().replace("\\","/");
        FileUtils.isValidPath(prePath);
        String formattedDate = FileUtils.getCurrentTimeUrl();
        String path = saveFolder+prePath+formattedDate+"/";
        FileUtils.createFolderIfAbenset(path);
        String filename = commonFileVO.getMd5()+"."+fileType;
        logger.info("userDir:%s",System.getProperty("user.dir"));
        logger.info("savePath:%s",path);
        File loacalFile = new File(path,filename);
        try {
            SingleFile file = SingleFile.builder()
                    .md5(commonFileVO.getMd5())
                    .path(path)
                    .originName(commonFileVO.getFile().getOriginalFilename())
                    .build();
            commonFileVO.getFile().transferTo(loacalFile);
            fileMapper.insert(file);
            String url = systemConfig.getDomain()+path+filename;
            return UploadFileVO.builder()
                    .fileId(String.valueOf(file.getId()))
                    .url(url)
                    .build();
        } catch (IOException e) {
            logger.error(e.toString(),e);
        }
        return null;
    }
}
