package info.jab.reactive.ch0.euler;

import java.util.function.Predicate;
import java.util.stream.LongStream;
import org.apache.commons.lang3.NotImplementedException;

/**
 * Problem 10: Summation of primes
 * https://projecteuler.net/problem=10
 *
 * The sum of the primes below 10 is 2 + 3 + 5 + 7 = 17.
 *
 * Find the sum of all the primes below two million.
 */
public class EulerProblem10 {

    public Long JavaSolution(long limit) {

        throw new NotImplementedException("¯\\_(ツ)_/¯");
    }

    Predicate<Long> isPrime = number ->
            LongStream.rangeClosed(2, number / 2)
                .noneMatch(i -> number % i == 0);

    public long JavaStreamSolution(long limit) {
         return LongStream.iterate(1, i -> i + 1)
                .filter(isPrime::test)
                .skip(1)
                .takeWhile(number -> number <= limit)
                .sum();
    }
}
