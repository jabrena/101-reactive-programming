package info.jab.reactive.ch0.euler;

import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * Problem 20: Factorial digit sum
 * n! means n (n 1) ... 3 2 1
 *
 * For example, 10! = 10   9   ...   3   2   1 = 3628800,
 *
 * and the sum of the digits in the number 10! is
 * 3 + 6 + 2 + 8 + 8 + 0 + 0 = 27.
 *
 * Find the sum of the digits in the number 100!
 *
 */
public class EulerProblem20 {

    Function<Long, BigInteger> factorial = limit -> IntStream.iterate(limit.intValue(), i -> i - 1)
            .limit(limit)
            .mapToObj(BigInteger::valueOf)
            .reduce((n1, n2) -> n1.multiply(n2)).get();

    Function<BigInteger, Long> sumDigits = value -> value.toString().chars()
            .mapToObj(c -> String.valueOf((char) c))
            .mapToLong(Long::valueOf)
            .reduce(0L, Long::sum);

    public Mono<Long> ReactorSolution(Long limit) {

        return Mono.just(limit)
                .map(factorial)
                .map(sumDigits);
    }

}
