package com.spixel.SpixelFigterApi.exception;

public class UserNotFoundException extends Throwable{

    private String exceptionDetail;
    private Object fielValue;

    public UserNotFoundException(String exceptionDetail, Object fielValue) {
        this.exceptionDetail = exceptionDetail;
        this.fielValue = fielValue;
    }

    public String getExceptionDetail() {
        return exceptionDetail;
    }

    public Object getFielValue() {
        return fielValue;
    }
}
