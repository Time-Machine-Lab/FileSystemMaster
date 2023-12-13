package com.example.filesystem.core.oss;

import com.example.filesystem.pojo.vo.DownloadFileVO;
import com.example.filesystem.pojo.vo.OSSFileVO;
import com.example.filesystem.pojo.vo.UploadFileVO;

/**
 * @Description
 * @Author welsir
 * @Date 2023/11/30 18:07
 */
public interface OSSFileOperatorInterface {

    UploadFileVO uploadFile(OSSFileVO ossFileVO);

    String downloadFile(DownloadFileVO ossFileVO);

}
