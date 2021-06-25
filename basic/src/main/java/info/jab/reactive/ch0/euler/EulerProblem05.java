package info.jab.reactive.ch0.euler;

import io.vavr.collection.Stream;
import java.util.function.BiPredicate;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import org.apache.commons.lang3.NotImplementedException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Problem 5: Smallest multiple
 * 2520 is the smallest number that can be divided by each of the numbers
 * from 1 to 10 without any remainder.
 *
 * What is the smallest positive number that is evenly divisible
 * by all of the numbers from 1 to 20?
 *
 */
public class EulerProblem05 {

    public Long JavaSolution(Long limit) {

        throw new NotImplementedException("¯\\_(ツ)_/¯");
    }

    BiPredicate<Long, Long> checkDivisible = (value, limit) ->
            IntStream.rangeClosed(1, limit.intValue()).boxed()
                    .map(j -> value % j == 0)
                    .reduce(Boolean::logicalAnd).get();

    public Long JavaStreamSolution(Long limit) {

        return LongStream.rangeClosed(1, Long.MAX_VALUE).boxed()
                .filter(i -> checkDivisible.test(i, limit))
                .findFirst().get();
    }

    long pow(long a, long p) {
        return Stream.rangeClosed(1, p).fold(1L, (xs, x) -> xs * a);
    }

    public Mono<Long> ReactorSolution(Long limit) {

         Flux.range(2, limit.intValue())
                .filter(i -> checkDivisible.test(Long.valueOf(i), limit))
                .map(i -> Long.valueOf(i))
                .doOnSubscribe(System.out::println);

        return Mono.just(0L);
    }

}
