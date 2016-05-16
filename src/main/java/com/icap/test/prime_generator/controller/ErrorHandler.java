package com.icap.test.prime_generator.controller;

import com.icap.test.prime_generator.domain.ApiError;
import com.icap.test.prime_generator.exception.InvalidParameterException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Error handler for the controler
 * Created by saflinhussain.
 */
@ControllerAdvice
public class ErrorHandler {

    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidParameterException.class)
    public ApiError handleInvalidParmeters(final InvalidParameterException exception) {
        return new ApiError(exception.getMessage(), exception.getExpectedParameter(),exception.getParameter());
    }
}
