package com.revature.RevWorkforce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Email is already in use")
public class EmailInUseException extends RuntimeException {
    /**
     * Creates an EmailInUseException
     */
    public EmailInUseException() {
        super();
    }

    /**
     * Creates an EmailInUseException with a message (text) included with it
     *
     * @param message the message to include with the exception
     */
    public EmailInUseException(String message) {
        super(message);
    }
}