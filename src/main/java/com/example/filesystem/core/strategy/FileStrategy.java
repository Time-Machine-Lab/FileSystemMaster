package com.example.filesystem.core.strategy;

import com.example.filesystem.pojo.vo.CommonFileVO;
import com.example.filesystem.pojo.vo.UploadFileVO;

import java.util.List;

/**
 * @Description
 * @Author welsir
 * @Date 2023/11/30 15:24
 */
public abstract class FileStrategy {

    public abstract <T extends CommonFileVO> String download(T commonFileVO);

    public abstract <T extends CommonFileVO> UploadFileVO upload(T commonFileVO);

    public abstract <T extends CommonFileVO> List<UploadFileVO> upload(List<T> commonFileVOList);
}
