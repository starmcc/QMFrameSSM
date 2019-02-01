package com.qm.frame.qmsecurity.exception;

/**
 * Copyright © 2019浅梦工作室. All rights reserved.
 *
 * @author 浅梦
 * @date 2019/2/2 1:47
 * @Description 权限框架异常类
 */
public class QmSecurityException extends RuntimeException{

    public QmSecurityException() {
        super();
    }

    public QmSecurityException(String message, Throwable cause) {
        super(message, cause);
    }

    public QmSecurityException(String message) {
        super(message);
    }
}
