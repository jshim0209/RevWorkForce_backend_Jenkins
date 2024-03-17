package com.revature.RevWorkforce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Account not found in db!")
public class AccountNotFound extends RuntimeException {
    public AccountNotFound() {
        super();
    }
    public AccountNotFound(String message) {
        super(message);
    }
}
