package com.example.filesystem.util;

import com.example.filesystem.common.BaseException;
import com.example.filesystem.common.log.AbstractLogger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @Description
 * @Author welsir
 * @Date 2023/11/24 19:56
 */
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
    public static String getFileFolderPath(String identifier) {
        return identifier.charAt(0) + File.separator +
                identifier.charAt(1) + File.separator +
                identifier + File.separator;
    }

    public static File createFolderIfAbenset(String folderPath){
        File folder = new File(folderPath);
        if(!folder.exists()||!folder.isDirectory()){
            folder.mkdirs();
        }
        return folder;
    }

    public static boolean isValidPath(String pathString) {
        try {
            Path path = Paths.get(pathString);
            return path != null;
        } catch (Exception e) {
            throw new BaseException(e.toString());
        }
    }

    public static Integer translateByteToMB(Long b){
        return Math.toIntExact(b / (1024 * 1024));
    }

    public static String getExtension(String filename) {
        int dotIndex = filename.lastIndexOf(".");
        if (dotIndex >= 0 && dotIndex < filename.length() - 1) {
            return filename.substring(dotIndex + 1);
        }
        return "";
    }

    public static String getCurrentTimeUrl(){
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return currentDate.format(formatter);
    }
}
