package com.revature.RevWorkforce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Start date cannot be after the end date")
public class LeaveDateMismatch extends Exception {
}
