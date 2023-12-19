package com.example.filesystem.service;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Description
 * @Author welsir
 * @Date 2023/12/12 20:23
 */
@Service
public class TestService {



    @Async
    @Transactional
    public void async(){
        System.out.println("主线程入口" + Thread.currentThread().getName());

    }

}
