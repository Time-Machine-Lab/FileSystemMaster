package com.example.filesystem.controller;

import com.example.filesystem.core.oss.AbstractOSS;
import com.example.filesystem.pojo.vo.OSSFileDownloadVO;
import com.example.filesystem.pojo.vo.OSSFileVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author welsir
 * @Date 2023/11/30 17:39
 */
@RequestMapping("/file/oss")
@RestController
public class OSSOperatorController extends FileController<OSSFileVO, AbstractOSS>{

}
