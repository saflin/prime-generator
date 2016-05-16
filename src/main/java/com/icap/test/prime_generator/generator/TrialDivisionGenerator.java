package com.icap.test.prime_generator.generator;

import com.icap.test.prime_generator.controller.PrimeGeneratorController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Generates prime no by trial division.
 * Created by saflinhussain.
 */
@Component
public class TrialDivisionGenerator implements PrimeGenerator {

    private static final Logger LOG = LoggerFactory.getLogger(TrialDivisionGenerator.class);
    public static final String TRIAL_DIVISION = "TRIAL_DIVISION";

    @Override
    public List<Integer> generatePrimeNumber(final Integer ceiling) {
        LOG.info(String.format("Generating prime numbers up to %d using %s",ceiling,TRIAL_DIVISION));
        validate(ceiling);
        return IntStream.rangeClosed(2, ceiling).boxed().parallel().filter(isPrime).collect(Collectors.toList());
    }

    @Override
    public String getGeneratorName() {
        return TRIAL_DIVISION;
    }

    private Predicate<Integer> isPrime = (number -> {
        int candidateRoot = (int) Math.sqrt((double) number);
        return IntStream.rangeClosed(2, candidateRoot).noneMatch(i -> number % i == 0);
    });

}
