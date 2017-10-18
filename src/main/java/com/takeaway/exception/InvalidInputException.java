package com.takeaway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid Input.")
public class InvalidInputException extends Exception {

    private static final long serialVersionUID = 7786568151454294523L;

    public InvalidInputException(String message) {
        super(message);
    }

}
