package com.w2m.spaceshiptask.utils.exception;

import com.w2m.spaceshiptask.utils.exception.messages.ExceptionMessages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFoundException extends W2mBaseException {


    public NotFoundException(String message) {
        super(message);
    }
}
