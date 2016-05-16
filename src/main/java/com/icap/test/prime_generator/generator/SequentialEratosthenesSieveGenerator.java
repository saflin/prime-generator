package com.icap.test.prime_generator.generator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Implementation of Erathoshenes Sieve prime number
 * generation algorithm
 * Created by saflinhussain.
 */
@Component
public class SequentialEratosthenesSieveGenerator implements PrimeGenerator {

    private static final Logger LOG = LoggerFactory.getLogger(SequentialEratosthenesSieveGenerator.class);

    public static final String ERATOSTHENES_SIEVE = "ERATOSTHENES_SIEVE_SEQUENTIAL";

    @Override
    public List<Integer> generatePrimeNumber(Integer ceiling) {
        validate(ceiling);
        if (ceiling < 2) return Collections.emptyList();
        //by default bit set value is false. false == prime number
        BitSet bitSet = new BitSet((ceiling+1));
        int sqrt = (int) Math.sqrt(ceiling);
        IntStream.rangeClosed(2, sqrt).boxed().forEach(number -> {
            if (!bitSet.get(number)) {
                for (long i = number * number; i <= ceiling; i += number) {
                    bitSet.set((int) i);
                }
            }
        });

        return IntStream.rangeClosed(2, ceiling).boxed().filter(number -> {
            return !bitSet.get(number);
        }).collect(Collectors.toList());
    }

    @Override
    public String getGeneratorName() {
        return ERATOSTHENES_SIEVE;
    }
}
