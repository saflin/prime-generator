package com.icap.test.prime_generator.exception;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by saflinhussain.
 */
public class InvalidParameterExceptionTest {

    private InvalidParameterException underTest;

    @Test
    public void itShouldHoldAndReturnCorrectData(){
        underTest = new InvalidParameterException("Test message","parameter 1", "expected parameter");
        assertEquals("Test message",underTest.getMessage());
        assertEquals("parameter 1",underTest.getParameter());
        assertEquals("expected parameter",underTest.getExpectedParameter());
    }

}