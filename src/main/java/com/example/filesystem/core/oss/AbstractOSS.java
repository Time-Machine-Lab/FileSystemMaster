package com.example.filesystem.core.oss;

import com.example.filesystem.core.strategy.FileStrategy;
import com.example.filesystem.pojo.vo.CommonDownloadVO;
import com.example.filesystem.pojo.vo.CommonFileVO;
import com.example.filesystem.pojo.vo.OSSFileDownloadVO;
import com.example.filesystem.pojo.vo.OSSFileVO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description
 * @Author welsir
 * @Date 2023/11/30 17:27
 */
@Component
public class AbstractOSS extends FileStrategy{

    @Resource(name = "${oss.handler}")
    OSSFileOperatorInterface ossOperator;

    @Override
    public <T extends CommonFileVO> String download(T commonFileVO) {
        return ossOperator.downloadFile((OSSFileDownloadVO) commonFileVO);
    }

    @Override
    public <T extends CommonFileVO> String upload(T commonFileVO) {
        return ossOperator.uploadFile((OSSFileVO) commonFileVO);
    }
}
