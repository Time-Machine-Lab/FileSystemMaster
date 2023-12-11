package com.example.filesystem.core.strategy;

import com.example.filesystem.pojo.vo.CommonFileVO;
import com.example.filesystem.pojo.vo.UploadFileVO;

/**
 * @Description
 * @Author welsir
 * @Date 2023/11/30 15:24
 */
public abstract class FileStrategy {

    public abstract <T extends CommonFileVO> String download(T commonFileVO);

    public abstract <T extends CommonFileVO> UploadFileVO upload(T commonFileVO);
}
