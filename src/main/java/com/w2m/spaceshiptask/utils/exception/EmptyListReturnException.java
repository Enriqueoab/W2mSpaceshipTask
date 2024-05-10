package com.w2m.spaceshiptask.utils.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NO_CONTENT)
public class EmptyListReturnException extends W2mBaseException {


    public EmptyListReturnException(String message) {
        super(message);
    }
}
