package com.ecommerce.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class APIException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    @Getter
    private HttpStatus status;

    private String message;

    public APIException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public APIException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

    public APIException() {
    }

    public APIException(String message) {
        super(message);
    }


}
