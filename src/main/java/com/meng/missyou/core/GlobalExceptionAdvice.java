package com.meng.missyou.core;

import com.meng.missyou.exception.http.HttpException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionAdvice {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public String handleException(HttpServletRequest request,Exception e){
        System.out.println("hello");
        UnifyResponse message = new UnifyResponse(9999,"服务器异常","url");
        return "String";
    }
    @ExceptionHandler(HttpException.class)
    public void handleHttpException(HttpServletRequest request, HttpException e){
        System.out.printf("hello");
    }
}
