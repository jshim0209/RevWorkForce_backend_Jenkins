package com.revature.RevWorkforce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid Account Creation Data")
public class InvalidAccountCreationDataException extends RuntimeException {
    /**
     * Creates an InvalidAccountCreationDataException
     */
    public InvalidAccountCreationDataException() {
        super();
    }

    /**
     * Creates an InvalidAccountCreationDataException with a message (text) included with it
     *
     * @param message the message to include with the exception
     */
    public InvalidAccountCreationDataException(String message) {
        super(message);
    }
}