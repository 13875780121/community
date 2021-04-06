package com.imnoob.community.exception;

public class CustomizeException extends RuntimeException {
    private String msg;
    private Integer code;
    public CustomizeException(String msg,Integer code) {
        this.msg = msg;
        this.code = code;
    }

    public CustomizeException(ExceptionEnum exceptionEnum) {
        this.msg = exceptionEnum.getMessage();
        this.code = exceptionEnum.getCode();
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
