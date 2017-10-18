package com.takeaway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Wrong Move.")
public class WrongMoveException extends Exception
{

    private static final long serialVersionUID = 7990811753633242333L;

    public WrongMoveException(String message)
    {
        super(message);
    }

}
