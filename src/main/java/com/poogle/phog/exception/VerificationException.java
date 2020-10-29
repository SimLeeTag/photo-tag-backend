package com.poogle.phog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class VerificationException extends RuntimeException {
    public VerificationException(String message) {
        super(message);
    }
}
