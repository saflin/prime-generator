package com.icap.test.prime_generator.generator;

import com.icap.test.prime_generator.exception.InvalidParameterException;

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
            throw new InvalidParameterException("ceiling cannot be -ve", String.valueOf(ceiling), "ceiling should be > 0");
    }
}
