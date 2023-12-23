package com.example.filesystem.exception;

import com.example.filesystem.common.BaseException;
import com.example.filesystem.common.Result;
import com.example.filesystem.common.log.AbstractLogger;
import com.example.filesystem.pojo.StatusConstEnum;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.validation.BindException;

import java.util.stream.Collectors;

/**
 * @Description 同意异常类处理器
 * @Author welsir
 * @Date 2023/12/1 20:59
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @Resource
    AbstractLogger logger;

    @ExceptionHandler(BaseException.class)
    public Result handleException(HttpServletRequest request,
                                  Exception ex) {
        logger.error("Handle Exception Request Url:%s,Exception:%s", request.getRequestURL(), ex);
        Result result;
        //系统异常
        if (ex instanceof BaseException) {
            BaseException se = (BaseException) ex;
            StatusConstEnum resultCode = se.getResultCode();
            if (resultCode == null) {
                result = Result.fail(se.getMessage());
            } else {
                String message = StringUtils.isEmpty(se.getMessage()) ? resultCode.getDesc()  : ex.getMessage();
                result = Result.fail(resultCode.getCode()
                ,message);
            }
        }
        //参数错误
        else {
            result = new Result(StatusConstEnum.SYSTEM_ERROR.getCode(), ex.getMessage());
        }
        logger.info("exception handle result:" + result);
        return result;
    }


    @ExceptionHandler(BindException.class)
    @ResponseBody
    public Result bindExceptionHandler(BindException e){
        String message = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
        return Result.fail(message);
    }

}
