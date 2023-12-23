package com.example.filesystem.util;

import com.example.filesystem.common.BaseException;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @Description
 * @Author welsir
 * @Date 2023/12/23 20:03
 */
public class ConcurrentUtil {

    public static <T> Future<T> doJob(ExecutorService executorService, Callable<T> callable) {
        return executorService.submit(callable);
    }

    public static <T> T futureGet(Future<T> future) {
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new BaseException(e.toString());
        }
    }

}
