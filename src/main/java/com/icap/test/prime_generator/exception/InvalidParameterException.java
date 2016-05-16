package com.icap.test.prime_generator.exception;

/**
 * Created by saflinhussain on 30/04/2016.
 */
public class InvalidParameterException extends RuntimeException {

    private String parameter;

    private String expectedParameter;

    public InvalidParameterException(final String message, final String parameter, final String expectedParameter) {
        super(message);
        this.parameter = parameter;
        this.expectedParameter = expectedParameter;
    }

    public String getParameter() {
        return parameter;
    }

    public String getExpectedParameter() {
        return expectedParameter;
    }

}
