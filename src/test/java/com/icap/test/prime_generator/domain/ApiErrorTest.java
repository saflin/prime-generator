package com.icap.test.prime_generator.domain;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by saflinhussain.
 */
public class ApiErrorTest {

    private ApiError underTest;

    @Test
    public void itShouldHoldAndReturnCorrectData(){
        underTest = new ApiError("error","test message","test data");
        assertEquals("error",underTest.getError());
        assertEquals("test data",underTest.getData());
        assertEquals("test message",underTest.getMessage());
    }

    @Test
    public void itShouldHandleNullGraceFully(){
        underTest = new ApiError(null,null,null);
        assertNull(underTest.getData());
        assertNull(underTest.getMessage());
        assertNull(underTest.getError());
    }


}