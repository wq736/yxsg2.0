package com.yxsg.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 验证码错误异常
 */
@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
public class CodeException extends RuntimeException{
    public static final long serialVersionUID = 406L;
    public CodeException(){}
    public CodeException(String msg){super(msg); }
}
