package com.example.filesystem.core.strategy;

import com.example.filesystem.pojo.vo.CommonDownloadVO;
import com.example.filesystem.pojo.vo.CommonFileVO;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author welsir
 * @Date 2023/11/30 15:24
 */
@Component
public abstract class FileStrategy {

    public abstract <T extends CommonFileVO> String download(T commonFileVO);

    public abstract <T extends CommonFileVO> String upload(T commonFileVO);
}
