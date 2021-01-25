package com.poogle.phog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AuthorizationException extends RuntimeException {

    public static AuthorizationException emptyToken() {
        return new AuthorizationException("Token doesn't exist");
    }

    public static AuthorizationException accessWrong() {
        return new AuthorizationException("Not allowed access");
    }

    public AuthorizationException(String message) {
        super(message);
    }
}
