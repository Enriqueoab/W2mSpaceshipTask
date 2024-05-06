package com.w2m.spaceshiptask.utils.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class W2mBaseException extends Exception {

    public W2mBaseException(String message) {
        super(message);
    }
}
