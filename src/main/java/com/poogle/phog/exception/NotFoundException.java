package com.poogle.phog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    public static NotFoundException noteNotFound() {
        return new NotFoundException("Note doesn't exist");
    }

    public NotFoundException(String message) {
        super(message);
    }
}
