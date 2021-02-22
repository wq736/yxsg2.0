package com.yxsg.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class NumberException extends RuntimeException{
    private static final long serialVersionUID = 400L;
    public NumberException(){}
    public NumberException(String msg){ super(msg); }
}
