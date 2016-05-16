package com.icap.test.prime_generator.generator;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.icap.test.prime_generator.TestData.*;
import static org.junit.Assert.*;

/**
 * Created by saflinhussain on 16/05/2016.
 */
public abstract class AbstractPrimeGeneratorTest {

    protected abstract PrimeGenerator getUnderTest();

    protected abstract String getUnderTestName();

    @Test
    public void itShouldGeneratePrimeNumber(){
        List<Integer> primes =  getUnderTest().generatePrimeNumber(10);
        assertTrue(primes.containsAll(Arrays.asList(PRIME_10)));

        primes =  getUnderTest().generatePrimeNumber(100);
        assertTrue(primes.containsAll(Arrays.asList(PRIME_100)));

        primes =  getUnderTest().generatePrimeNumber(1000);
        assertTrue(primes.containsAll(Arrays.asList(PRIME_1000)));

        primes =  getUnderTest().generatePrimeNumber(10000);
        assertTrue(primes.containsAll(Arrays.asList(PRIME_10000)));
    }

    @Test
    public void itShouldThrowExceptionIfCeilingIsNull(){
        try {
            getUnderTest().generatePrimeNumber(null);
            fail("expecting exception here.");
        }catch (IllegalArgumentException ex){
            assertEquals("ceiling cannot be null",ex.getMessage());
        }
    }

    @Test
    public void itShouldThrowExceptionIfCeilingIsNegative(){
        try {
            getUnderTest().generatePrimeNumber(-10);
            fail("expecting exception here.");
        }catch (IllegalArgumentException ex){
            assertEquals("ceiling cannot be -ve",ex.getMessage());
        }
    }

    @Test
    public void itShouldReturnGeneratorName(){
        assertEquals(getUnderTestName(), getUnderTest().getGeneratorName());
    }

}
