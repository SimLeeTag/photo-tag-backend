package com.poogle.phog.web.admin.user.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ResponseDTO {

    private int statusCode;

    private String message;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Object data;

    public ResponseDTO(int statusCode, String message, Object data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public ResponseDTO(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
