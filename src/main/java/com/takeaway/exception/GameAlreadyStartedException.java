package com.takeaway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Game Already Started.")
public class GameAlreadyStartedException extends Exception
{

    private static final long serialVersionUID = -4713570515946130375L;

    public GameAlreadyStartedException(String message)
    {
        super(message);
    }

}
