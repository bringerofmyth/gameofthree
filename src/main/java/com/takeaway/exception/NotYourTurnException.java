package com.takeaway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "It's not your turn.")
public class NotYourTurnException extends Exception
{

    private static final long serialVersionUID = 8918101036649927031L;

    public NotYourTurnException(String message)
    {
        super(message);
    }

}
