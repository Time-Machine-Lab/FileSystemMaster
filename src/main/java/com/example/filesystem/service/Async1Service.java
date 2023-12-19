package com.example.filesystem.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description
 * @Author welsir
 * @Date 2023/12/12 20:36
 */
@Component
public class Async1Service {

    @Resource
    Async2Service async2Service;

    @Async
    public void async1(){
        async2Service.async2();
    }



}
