package com.xuecheng.base.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @description TODO
 * @date 2023/3/7 8:35
 */
@ControllerAdvice //控制器增强
@Slf4j
public class GlobalExceptionHandler {
    //处理XueChengPlusException异常，此类异常是程序员主动抛出的异常，可预知异常
    @ResponseBody //返回json格式数据
    @ExceptionHandler(XueChengPlusException.class) //此方法捕获XueChengPlusException异常
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) //状态码返回500
    public RestErrorResponse doXueChengPlusException(XueChengPlusException e){
        log.error("捕获异常：{}",e.getErrMessage());
        e.printStackTrace();
        String errMessage = e.getErrMessage();
        return new RestErrorResponse(errMessage);
    }

    //铺货不可预知异常
    @ResponseBody //返回json格式数据
    @ExceptionHandler(MethodArgumentNotValidException.class) //此方法捕获MethodArgumentNotValidException异常
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) //状态码返回500
    public RestErrorResponse doMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        //校验的错误信息
        List<FieldError> fieldErrors = result.getFieldErrors();
        //收集错误
        StringBuffer errors=new StringBuffer();
        fieldErrors.forEach(error->{
            errors.append(error.getDefaultMessage()).append(",");
        });
        return new RestErrorResponse(errors.toString());
    }
    @ResponseBody //返回json格式数据
    @ExceptionHandler(Exception.class) //此方法捕获Exception异常
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) //状态码返回500
    public RestErrorResponse doException(Exception e) {
        log.error("捕获异常：{}", e.getMessage());
        e.printStackTrace();

        return new RestErrorResponse(CommonError.UNKOWN_ERROR.getErrMessage());
    }
}
