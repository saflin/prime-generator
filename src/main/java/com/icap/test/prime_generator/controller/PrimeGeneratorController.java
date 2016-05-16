package com.icap.test.prime_generator.controller;

import com.icap.test.prime_generator.domain.ApiResponse;
import com.icap.test.prime_generator.exception.InvalidParameterException;
import com.icap.test.prime_generator.generator.PrimeGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

/**
 * Controller for Prime generator endpoints.
 * Created by saflinhussain.
 */
@RestController
@RequestMapping("/generator")
public class PrimeGeneratorController {

    private static final Logger LOG = LoggerFactory.getLogger(PrimeGeneratorController.class);

    private List<PrimeGenerator> primeGenerators;

    @Autowired
    public PrimeGeneratorController(final List<PrimeGenerator> primeGenerators) {
        this.primeGenerators = primeGenerators;
    }

    /**
     * Generate prime numbers up to ceiling using supported algorithms.
     * @param algorithm name of prime generation algorithm
     * @param ceiling Value up to which prime numbers to be generated.
     * @return List of prime numbers.
     */
    @RequestMapping(value = "/prime", method = RequestMethod.GET)
    public ApiResponse generatePrime(@RequestParam(value = "algorithm", required = true) final String algorithm,
                                     @RequestParam(value = "ceiling", required = true) final String ceiling) {
        LOG.info(format("Received prime generate request: algorithm %s, ceiling %s ", algorithm, ceiling));
        validateCeiling(ceiling);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List list = getGenerator(algorithm).generatePrimeNumber(new Integer(ceiling));
        stopWatch.stop();
        LOG.info("Request processed in " + stopWatch.getTotalTimeSeconds() + " seconds");
        return new ApiResponse("Success", list);
    }

    private PrimeGenerator getGenerator(final String algorithm) {
        for (PrimeGenerator primeGenerator : primeGenerators) {
            if (primeGenerator.getGeneratorName().equals(algorithm)) {
                return primeGenerator;
            }
        }
        throw new InvalidParameterException("Invalid algorithm", "algoithm: " + algorithm,
                "Unable to find prime generator matching algorithm: " + algorithm);
    }


    private void validateCeiling(final String ceiling) {
        try {
            BigInteger value = new BigInteger(ceiling);
            if (value.compareTo(new BigInteger(String.valueOf(Integer.MAX_VALUE))) > 0) {
                throw new InvalidParameterException("Ceiling crossed max value limit:2147483647", "ceiling:" + ceiling,
                        "Ceiling should be less than " + Integer.MAX_VALUE);
            }
        } catch (NumberFormatException ex) {
            LOG.error(format("Invalid ceiling %s ", ceiling), ex);
            throw new InvalidParameterException(ex.getMessage(), "ceiling:" + ceiling,
                    "Ceiling should be numeric and non decimal value");
        }
    }

    /**
     * Gets all the supported prime generation algorithms.
     * @return
     */
    @RequestMapping(value = "/algorithms", method = RequestMethod.GET)
    public ApiResponse getPrimeGeneratorAlgorithms() {
        List<String> list = null;
        if (primeGenerators != null) {
            list = primeGenerators.stream()
                    .map(primeGenerator -> {
                        return primeGenerator.getGeneratorName();
                    })
                    .collect(Collectors.toList());
        } else {
            list = Collections.EMPTY_LIST;
        }
        return new ApiResponse("Success", list);
    }
}
