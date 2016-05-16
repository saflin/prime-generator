package com.icap.test.prime_generator.generator;

import org.junit.Before;

/**
 * Created by saflinhussain on 16/05/2016.
 */
public class TrialDivisionGeneratorTest extends AbstractPrimeGeneratorTest {

    private TrialDivisionGenerator underTest;

    @Before
    public void setUp() {
        underTest = new TrialDivisionGenerator();
    }


    @Override
    protected PrimeGenerator getUnderTest() {
        return underTest;
    }

    @Override
    protected String getUnderTestName() {
        return TrialDivisionGenerator.TRIAL_DIVISION;
    }
}