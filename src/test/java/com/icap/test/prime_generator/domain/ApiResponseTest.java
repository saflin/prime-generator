package com.icap.test.prime_generator.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by saflinhussain.
 */
public class ApiResponseTest {

    private ApiResponse underTest;

    @Test
    public void itShouldHoldAndReturnCorrectData(){
        underTest = new ApiResponse("Success","Test Object");
        assertEquals("Success",underTest.getResult());
        assertEquals("Test Object",underTest.getData());
    }

    @Test
    public void itShouldHandleNullGraceFully(){
        underTest = new ApiResponse(null,null);
        assertNull(underTest.getData());
        assertNull(underTest.getResult());
    }

}