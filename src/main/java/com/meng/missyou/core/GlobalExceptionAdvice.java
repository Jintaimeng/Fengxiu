package com.meng.missyou.core;

import com.meng.missyou.exception.http.HttpException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionAdvice {
    @ExceptionHandler(value = Exception.class)
    public void handleException(HttpServletRequest request,Exception e){
        System.out.println("hello");
    }
    @ExceptionHandler(HttpException.class)
    public void handleException(HttpServletRequest request, HttpException e){
        System.out.printf("hello");
    }
}
