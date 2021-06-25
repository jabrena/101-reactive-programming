package info.jab.reactive.ch0.euler;

import io.vavr.Function3;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.apache.commons.lang3.NotImplementedException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Problem 4: Largest palindrome product
 * https://projecteuler.net/problem=4
 *
 * A palindromic number reads the same both ways.
 *
 * The largest palindrome made from the product of two 2-digit numbers is 9009 = 91 × 99.
 *
 * Find the largest palindrome made from the product of two 3-digit numbers.
 *
 */
public class EulerProblem04 {

    public Integer JavaSolution(Integer min, Integer max) {

        throw new NotImplementedException("¯\\_(ツ)_/¯");
    }

    Function<Integer, String> reverse = number -> new StringBuilder().append(number).reverse().toString();

    Predicate<Integer> isPalindrome = value -> reverse.apply(value).equals(value.toString());

    //Kind of Crossproduct
    Function3<Integer, Integer, Integer, Stream<Integer>> multipliedList = (min, max, value) ->
            IntStream.rangeClosed(min, max).boxed()
                .map(element -> element * value);

    public Integer JavaStreamSolution(Integer min, Integer max) {

        return IntStream.rangeClosed(min, max).boxed()
                .flatMap(l -> multipliedList.apply(min, max, l))
                .filter(isPalindrome)
                .sorted(Comparator.reverseOrder())
                .findFirst()
                .get();

    }

    public Mono<Integer> ReactorSolution(Integer min, Integer max) {

        Function<Integer, Flux<Integer>> crossProduct = element ->
            Flux.range(min, max - min + 1)
                    .map(element2 -> element * element2);

        return Flux.range(min, max - min + 1)
                .flatMap(crossProduct)
                .filter(isPalindrome)
                .sort()
                .last();
    }

}
