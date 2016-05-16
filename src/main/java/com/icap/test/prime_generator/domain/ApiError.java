package com.icap.test.prime_generator.domain;

/**
 * Created by saflinhussain.
 */
public class ApiError {

    private String error;
    private String message;
    private Object data;

    public ApiError(final String error, final String message, final Object data) {
        this.error = error;
        this.message = message;
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
