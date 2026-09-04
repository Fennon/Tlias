package org.example.exception;

// 自定义异常类
public class BusinessException extends RuntimeException {
    public BusinessException(String msg) {
        super(msg);
    }
}