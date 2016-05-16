package com.icap.test.prime_generator.domain;

/**
 * Created by saflinhussain.
 */
public class ApiResponse {

    private String result;

    private Object data;

    public ApiResponse(final String result, final Object data) {
        this.result = result;
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


}
