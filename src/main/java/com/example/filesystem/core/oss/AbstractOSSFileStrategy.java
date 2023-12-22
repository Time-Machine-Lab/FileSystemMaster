package com.example.filesystem.core.oss;

import com.example.filesystem.core.strategy.FileStrategy;
import com.example.filesystem.pojo.vo.CommonFileVO;
import com.example.filesystem.pojo.vo.DownloadFileVO;
import com.example.filesystem.pojo.vo.OSSFileVO;
import com.example.filesystem.pojo.vo.UploadFileVO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        UploadFileVO uploadFileVO = ossOperator.uploadFile((OSSFileVO) commonFileVO);
        return uploadFileVO;
    }

    @Override
    public <T extends CommonFileVO> List<UploadFileVO> upload(List<T> commonFileVOList) {
        ArrayList<UploadFileVO> res = new ArrayList<>();
        for (T t : commonFileVOList) {
            UploadFileVO fileVO = ossOperator.uploadFile((OSSFileVO) t);
            res.add(fileVO);
        }
        return res;
    }
}
