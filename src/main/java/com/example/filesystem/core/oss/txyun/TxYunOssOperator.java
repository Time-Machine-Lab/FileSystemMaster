package com.example.filesystem.core.oss.txyun;

import com.example.filesystem.core.oss.OSSFileOperatorInterface;
import com.example.filesystem.pojo.vo.DownloadFileVO;
import com.example.filesystem.pojo.vo.OSSFileVO;
import com.example.filesystem.pojo.vo.UploadFileVO;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author welsir
 * @Date 2023/12/19 11:56
 */
@Component("txyun")
public class TxYunOssOperator implements OSSFileOperatorInterface {
    @Override
    public UploadFileVO uploadFile(OSSFileVO ossFileVO) {
        System.out.println("1111");
        return null;
    }

    @Override
    public String downloadFile(DownloadFileVO ossFileVO) {
        return null;
    }
}
