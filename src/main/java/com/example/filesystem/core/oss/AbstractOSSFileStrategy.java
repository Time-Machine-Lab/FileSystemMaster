package com.example.filesystem.core.oss;

import com.example.filesystem.core.strategy.FileStrategy;
import com.example.filesystem.pojo.vo.CommonFileVO;
import com.example.filesystem.pojo.vo.DownloadFileVO;
import com.example.filesystem.pojo.vo.OSSFileVO;
import com.example.filesystem.pojo.vo.UploadFileVO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description 抽象OSS策略
 * @Author welsir
 * @Date 2023/11/30 17:27
 */
@Component
public class AbstractOSSFileStrategy extends FileStrategy{

    @Resource(name = "${oss.handler}")
    OSSFileOperatorInterface ossOperator;

    public String download(DownloadFileVO commonFileVO) {
        return ossOperator.downloadFile(commonFileVO);
    }

    @Override
    public <T extends CommonFileVO> String download(T commonFileVO) {
        return null;
    }

    @Override
    public <T extends CommonFileVO> UploadFileVO upload(T commonFileVO) {
        return ossOperator.uploadFile((OSSFileVO) commonFileVO);
    }
}
