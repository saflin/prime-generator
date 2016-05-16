package com.icap.test.prime_generator.generator;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by saflinhussain.
 */
public interface PrimeGenerator {
    List<Integer> generatePrimeNumber(Integer ceiling);
    String  getGeneratorName();
    default void validate(Integer ceiling){

        if(ceiling == null)
            throw new IllegalArgumentException("ceiling cannot be null");

        if(ceiling < 0)
            throw new IllegalArgumentException("ceiling cannot be -ve");
    }
}
