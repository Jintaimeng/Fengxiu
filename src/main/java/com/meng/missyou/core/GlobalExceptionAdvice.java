package com.meng.missyou.core;

import com.meng.missyou.exception.http.HttpException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionAdvice {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public UnifyResponse handleException(HttpServletRequest request,Exception e){
        String requestURL = request.getRequestURI();
        UnifyResponse message = new UnifyResponse(9999,"服务器异常",requestURL);
        return message;
    }
    @ExceptionHandler(HttpException.class)
    public void handleHttpException(HttpServletRequest request, HttpException e){
        System.out.printf("hello");
    }
}
