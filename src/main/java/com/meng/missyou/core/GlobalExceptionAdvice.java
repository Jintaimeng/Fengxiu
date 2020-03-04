package com.meng.missyou.core;

import com.meng.missyou.exception.http.HttpException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
        String method = request.getMethod();
        UnifyResponse message = new UnifyResponse(9999,"服务器异常",method+"  "+requestURL);
        return message;
    }
    @ExceptionHandler(HttpException.class)
    public ResponseEntity handleHttpException(HttpServletRequest request, HttpException e){
        String requestURL = request.getRequestURI();
        String method = request.getMethod();
        UnifyResponse message = new UnifyResponse(e.getCode(),"xxxxxx",method+""+requestURL);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpStatus httpStatus = HttpStatus.resolve(e.getHttpStatusCode());
        ResponseEntity<UnifyResponse> r = new ResponseEntity<>(message,httpHeaders,httpStatus);
        return r;
    }
}
