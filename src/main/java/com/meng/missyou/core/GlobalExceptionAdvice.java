package com.meng.missyou.core;

import com.meng.missyou.core.configuration.ExceptionCodeConfiguration;
import com.meng.missyou.exception.http.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionAdvice {
    @Autowired
    private ExceptionCodeConfiguration codeConfiguration;

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public UnifyResponse handleException(HttpServletRequest request, Exception e) {
        String requestURL = request.getRequestURI();
        String method = request.getMethod();
        UnifyResponse message = new UnifyResponse(9999, "服务器异常", method + "  " + requestURL);
        return message;
    }

    @ExceptionHandler(HttpException.class)
    public ResponseEntity<UnifyResponse> handleHttpException(HttpServletRequest request, HttpException e) {
        String requestURL = request.getRequestURI();
        String method = request.getMethod();
        UnifyResponse message = new UnifyResponse(e.getCode(), codeConfiguration.getMessage(e.getCode()), method + "" + requestURL);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpStatus httpStatus = HttpStatus.resolve(e.getHttpStatusCode());
        ResponseEntity<UnifyResponse> r = new ResponseEntity<>(message, httpHeaders, httpStatus);
        return r;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public UnifyResponse handleBeanValidation(HttpServletRequest request, MethodArgumentNotValidException e) {
        String requestURL = request.getRequestURI();
        String method = request.getMethod();
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        String message = this.formatAllErrorMessages(errors);
        return new UnifyResponse(10001, message, method + " " + requestURL);
    }

    private String formatAllErrorMessages(List<ObjectError> errors) {
        StringBuffer errorMsg = new StringBuffer();
        errors.forEach(error -> errorMsg.append(error.getDefaultMessage()).append(";"));
        return errorMsg.toString();
    }
}
