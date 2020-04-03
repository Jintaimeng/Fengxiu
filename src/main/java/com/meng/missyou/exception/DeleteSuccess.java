package com.meng.missyou.exception;

import com.meng.missyou.exception.http.HttpException;

public class DeleteSuccess extends HttpException {
    public DeleteSuccess(int code) {
        this.httpStatusCode = 204;
        this.code = code;
    }
}