package com.revature.RevWorkforce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Leave request not found in db!")
public class LeaveRequestNotFound extends RuntimeException {
    public LeaveRequestNotFound() {
        super();
    }

    public LeaveRequestNotFound(String message) {
        super(message);
    }
}
