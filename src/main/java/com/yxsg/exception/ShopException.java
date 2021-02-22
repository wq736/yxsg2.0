package com.yxsg.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ShopException extends RuntimeException{
    private static final  long serialVersionUID= 404L;
    public ShopException(){}
    public ShopException(String msg){super(msg);}
}
