package com.w2m.spaceshiptask.utils.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.EXPECTATION_FAILED)
public class NotExpectedResultException extends W2mBaseException {


    public NotExpectedResultException(String message) {
        super(message);
    }
}
