package com.yxsg.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 名称异常
 */
@ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
public class NameException extends RuntimeException{
    private static final long serialVersionUID = 405L;
    public NameException() {};
    public NameException(String msg){ super(msg); }
}
