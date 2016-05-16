package com.icap.test.prime_generator.generator;

import org.junit.Before;

/**
 * Created by saflinhussain.
 */
public class ParallelEratosthenesSieveGeneratorTest extends AbstractPrimeGeneratorTest{

    private ParallelEratosthenesSieveGenerator underTest;

    @Before
    public void setUp(){
        underTest = new ParallelEratosthenesSieveGenerator();
    }

    @Override
    protected PrimeGenerator getUnderTest() {
        return underTest;
    }

    @Override
    protected String getUnderTestName() {
        return ParallelEratosthenesSieveGenerator.ERATOSTHENES_SIEVE_PARALLEL;
    }
}