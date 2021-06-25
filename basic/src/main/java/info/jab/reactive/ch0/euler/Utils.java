package info.jab.reactive.ch0.euler;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.apache.commons.lang3.tuple.Pair;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

/**
 * https://en.wikipedia.org/wiki/Fibonacci_number
 *
 * 0 1 1 2 3 5 8 13 21 34 55 89 144 233 377 610 987 1597 2584 4181 6765
 */
public class Utils {

    public static class Java {

        public static List<Long> fibonacci(long limit) {

            List<Long> fibonacciList = new ArrayList<>();

            long previousNumber = 1;
            long nextNumber = 2;

            while(previousNumber <= limit) {

                fibonacciList.add(previousNumber);

                long sum = previousNumber + nextNumber;
                previousNumber = nextNumber;
                nextNumber = sum;
            }

            return fibonacciList;
        }
    }

    public static class JavaStreams {

        public static Stream<BigInteger> fibonacci() {

            Pair<BigInteger, BigInteger> seed = Pair.of(BigInteger.ZERO, BigInteger.ONE);
            UnaryOperator<Pair<BigInteger, BigInteger>> operator =
                    x -> Pair.of(x.getRight(), x.getLeft().add(x.getRight()));

            return Stream.iterate(seed, operator)
                    .map(p -> p.getRight());
        }

        public static Stream<Long> fibonacci(long limit) {

            return fibonacci()
                    .skip(1)
                    .mapToLong(BigInteger::longValue)
                    .takeWhile(l -> l <= limit)
                    .mapToObj(l -> l);
        }

        static Function<Long, BigInteger> factorialStream = limit -> IntStream.iterate(limit.intValue(), i -> i - 1)
                .limit(limit)
                .mapToObj(BigInteger::valueOf)
                .reduce((n1, n2) -> n1.multiply(n2)).get();

        static Function<BigInteger, List<Long>> toDigits = value -> value.toString().chars()
                .mapToObj(c -> String.valueOf((char) c))
                .map(s -> Long.valueOf(s))
                .collect(Collectors.toList());

    }

    public static class Reactor {

        public static Flux<Long> fibonacci() {

            return Flux.generate(
                    () -> Tuples.of(1L, 2L),
                    (state, sink) -> {
                        sink.next(state.getT1());
                        return Tuples.of(state.getT2(), state.getT1() + state.getT2());
                    }
            );
        }

        public static Flux<Long> fibonacci(long limit) {

            return fibonacci().takeWhile(x-> x <= limit);
        }

    }

}
