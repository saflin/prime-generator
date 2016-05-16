package com.icap.test.prime_generator.generator;

import org.junit.Before;

/**
 * Created by saflinhussain.
 */
public class SequentialEratosthenesSieveGeneratorTest extends AbstractPrimeGeneratorTest {

    private SequentialEratosthenesSieveGenerator underTest;

    @Before
    public void setUp(){
        underTest = new SequentialEratosthenesSieveGenerator();
    }


    @Override
    protected PrimeGenerator getUnderTest() {
        return underTest;
    }

    @Override
    protected String getUnderTestName() {
        return SequentialEratosthenesSieveGenerator.ERATOSTHENES_SIEVE;
    }
}