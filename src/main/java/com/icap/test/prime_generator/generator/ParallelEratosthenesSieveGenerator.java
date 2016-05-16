package com.icap.test.prime_generator.generator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

/**
 * Multithreaded version of Eratoshenes Sieve.
 * Algorithm generates prime number using executor framework.
 *
 * Created by saflinhussain.
 */
@Component
public class ParallelEratosthenesSieveGenerator implements PrimeGenerator {

    private static final Logger LOG = LoggerFactory.getLogger(ParallelEratosthenesSieveGenerator.class);
    public static final String ERATOSTHENES_SIEVE_PARALLEL = "ERATOSTHENES_SIEVE_PARALLEL";


    /**
     * Generates Prime number up to ceiling value.
     *
     * 1) If ceiling is a small value, calculate prime using sequential eratosthenes sieve
     * implementaion.It not worth to use multithreading for small values.
     *
     * 2) Find prime number upto square root of ceiling using sequential eratosthenes sieve.
     *
     * 3) Split the number line to multiple segments.
     *
     * 4) Assign segments to executor to mark which will mark nom prime numbers in that segment.
     *
     * 5) Accumulate results from all segments.
     *
     * @param ceiling Integer value up to which prime number will be generated.
     * @return
     */
    @Override
    public List<Integer> generatePrimeNumber(Integer ceiling) {
        validate(ceiling);
        if (ceiling < 2) return Collections.emptyList();

        int availableProcessors = Runtime.getRuntime().availableProcessors();
        int sqrt = (int) Math.sqrt(ceiling);
        //its not worth using multi threading if the ceiling is low.
        if (sqrt < availableProcessors) {
            return new SequentialEratosthenesSieveGenerator().generatePrimeNumber(ceiling);
        }
        //find primes up to square root of number
        List<Integer> primes = new SequentialEratosthenesSieveGenerator().generatePrimeNumber(sqrt);
        LOG.info("Found small primes up to " + sqrt + "  " + primes);

        List<Future<Void>> futures = new LinkedList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        int remaingRange = (ceiling - sqrt);
        int segmentSize = remaingRange / Runtime.getRuntime().availableProcessors();
        int noOfSegments = remaingRange / segmentSize;

        LOG.info("Segment size : " + segmentSize + "   no of segments : " + noOfSegments);

        List<SegmentedSieve> segmentedSieves = createSegments(noOfSegments,segmentSize,sqrt,ceiling,primes);

        segmentedSieves.stream().forEach(segmentedSieve -> { futures.add(executorService.submit(segmentedSieve));});

        waitForCompletion(futures);

        executorService.shutdownNow();

        getResultsFromSegmentSieves(segmentedSieves, primes);

        return primes;
    }

    private List<SegmentedSieve> createSegments(int noOfSegments,int segmentSize,int startNumber,int ceiling,List<Integer> primes){
        int lower = startNumber + 1;
        int upper = lower + segmentSize;
        List<SegmentedSieve> segmentedSieves = new ArrayList<>(noOfSegments);
        for (int i = 1; i <= noOfSegments; i++) {
            SegmentedSieve segmentedSieve = new SegmentedSieve(lower, upper > ceiling ? ceiling : upper, primes);
            LOG.info("Segment " + i + " lower bound " + segmentedSieve.getLower() + "  upper bound " + segmentedSieve.getUpper());
            segmentedSieves.add(segmentedSieve);
            lower = upper + 1;
            upper = lower + segmentSize;
        }
        return segmentedSieves;
    }

    private void waitForCompletion(List<Future<Void>> futures) {
        futures.forEach(future -> {
            try {
                future.get();
            } catch (Exception ex) {
                LOG.error("Exception while getting future ", ex);
            }
        });
    }

    private void getResultsFromSegmentSieves(List<SegmentedSieve> segmentedSieves, List<Integer> primes) {
        IntStream.range(0, segmentedSieves.size()).forEach(n -> {
            SegmentedSieve segmentedSieve = segmentedSieves.get(n);
            for (int i = segmentedSieve.getLower(); i < segmentedSieve.getUpper(); i++) {
                if (!segmentedSieve.getBitSet().get(i - segmentedSieve.getLower())) {
                    primes.add(i);
                }
            }
        });
    }

    class SegmentedSieve implements Callable<Void> {

        private int lower;
        private int upper;
        private List<Integer> smallPrimes;
        private BitSet bitSet;

        SegmentedSieve(int lower, int upper, List<Integer> smallPrimes) {
            this.lower = lower;
            this.upper = upper;
            this.smallPrimes = smallPrimes;
        }

        @Override
        public Void call() throws Exception {
            int size = (upper - lower) + 1;
            this.bitSet = new BitSet(size);
            smallPrimes.forEach(prime -> {
                int remainder = lower % prime;
                int startIndex = remainder == 0 ? 0 : prime - remainder;
                for (int i = startIndex; i <= size; i += prime) {
                    if (!bitSet.get(i)) {
                        bitSet.set(i);
                    }
                }
            });
            return null;
        }

        public BitSet getBitSet() {
            return this.bitSet;
        }

        public int getUpper() {
            return upper;
        }

        public int getLower() {
            return lower;
        }
    }

    @Override
    public String getGeneratorName() {
        return ERATOSTHENES_SIEVE_PARALLEL;
    }

}
