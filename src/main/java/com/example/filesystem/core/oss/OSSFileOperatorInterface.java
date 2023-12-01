package com.example.filesystem.core.oss;

import com.example.filesystem.pojo.vo.OSSFileVO;

/**
 * @Description
 * @Author welsir
 * @Date 2023/11/30 18:07
 */
public interface OSSFileOperatorInterface {

    String uploadFile(OSSFileVO ossFileVO);

    String downloadFile(OSSFileVO ossFileVO);

}
