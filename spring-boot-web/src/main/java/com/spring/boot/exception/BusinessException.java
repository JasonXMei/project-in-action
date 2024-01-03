package com.spring.boot.exception;

import com.spring.boot.enums.ResponseCodeEnum;

/**
 * @Author Jason
 * @Date 2023-04-25
 */
public class BusinessException extends RuntimeException {

    private ResponseCodeEnum responseCode;

    public BusinessException(ResponseCodeEnum responseCode, String extraMessage) {
        super(extraMessage);
        this.responseCode = responseCode;
    }

    public ResponseCodeEnum getResponseCode() {
        return responseCode;
    }

}
