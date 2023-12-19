package com.example.filesystem.common;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.example.filesystem.pojo.StatusConstEnum;
import org.springframework.lang.Nullable;

/**
 * @Description
 * @Author welsir
 * @Date 2023/12/19 12:05
 */
public abstract class AbstractAssert {

    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new BaseException(message);
        }
    }
    public static void isBlank(String str, StatusConstEnum message) {
        if (StringUtils.isBlank(str)) {
            throw new BaseException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new BaseException(message);
        }
    }

    public static void isNull(Object object, StatusConstEnum message) {
        if (object == null) {
            throw new BaseException(message);
        }
    }

    public static void isNull(String str, StatusConstEnum message) {
        if (StringUtils.isBlank(str)) {
            throw new BaseException(message);
        }
    }

    public static void notNull(@Nullable Object object, StatusConstEnum message) {
        if (object != null) {
            throw new BaseException(message);
        }
    }
    public static void notNull(String str, StatusConstEnum message) {
        if (StringUtils.isBlank(str)) {
            throw new BaseException(message);
        }
    }

    public static void isTrue(boolean expression,StatusConstEnum message){
        if(!expression){
            throw new BaseException(message);
        }
    }

}
