package com.revature.RevWorkforce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Leave date range cannot be more than 14 days.")
public class LeaveDateRangeTooLarge extends Exception {
}
