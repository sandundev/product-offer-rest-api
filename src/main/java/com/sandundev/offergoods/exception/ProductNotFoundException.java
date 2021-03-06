package com.sandundev.offergoods.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 3021498590398202242L;

    public ProductNotFoundException(String message){
    	super(message);
    }
}
