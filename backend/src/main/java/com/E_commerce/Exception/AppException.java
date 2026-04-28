package com.E_commerce.Exception;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException{
    public AppException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorcode = errorCode;
    }
    private ErrorCode errorcode;
}
