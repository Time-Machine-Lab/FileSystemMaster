package com.example.filesystem.util;

import com.example.filesystem.common.log.AbstractLogger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;

/**
 * @Description
 * @Author welsir
 * @Date 2023/11/24 19:56
 */
@Component
public class FileUtils {


    @Resource
    AbstractLogger logger;



    /**
     * @description 获取分块存储文件路径
     * @param md5
     * @return String
     */
    public String getChunkFileFolderPath(String md5){
        return getFileFolderPath(md5) + "chunks" + File.separator;
    }

    /**
     * 得到文件所属的目录
     *
     * @param identifier md5
     * @return String
     */
    public String getFileFolderPath(String identifier) {
        return identifier.charAt(0) + File.separator +
                identifier.charAt(1) + File.separator +
                identifier + File.separator;
    }

    public File createFolderIfAbenset(String folderPath){
        File folder = new File(folderPath);
        if(!folder.exists()||!folder.isDirectory()){
            boolean mkdirs = folder.mkdirs();
            if(mkdirs){
                logger.info("目录不存在,新建文件目录:%s",folderPath);
            }
        }
        return folder;
    }

    public Integer translateByteToMB(Long b){
        return Math.toIntExact(b / (1024 * 1024));
    }
}
