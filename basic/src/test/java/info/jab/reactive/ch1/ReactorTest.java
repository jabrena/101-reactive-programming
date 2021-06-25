package info.jab.reactive.ch1;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.util.function.Tuples;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReactorTest {

    @Test
    public void testSimpleStringFlux() {

        var list = List.of("Quick", "brown", "fox", "jumped", "over", "the", "wall");
        var flux = Mono.just(list).flatMapMany(Flux::fromIterable);
        Flux<String> stingFlux = Flux.just("Quick", "brown", "fox", "jumped", "over", "the", "wall");

        StepVerifier
                .create(flux)
                .expectNext("Quick")
                .expectNext("brown")
                .expectNext("fox")
                .expectNext("jumped")
                .expectNext("over")
                .expectNext("the")
                .expectNext("wall")
                .expectComplete()
                .verify();

        StringBuilder str = new StringBuilder();
        stingFlux.subscribe(t -> {
            str.append(t).append(" ");
        });
        assertEquals("Quick brown fox jumped over the wall ", str.toString());
    }


    @Test
    public void testFibonacci() {
        Flux<Long> fibonacciGenerator = Flux.generate(
                () -> Tuples.<Long, Long>of(0L, 1L),
                (state, sink) -> {
                    sink.next(state.getT1());
                    return Tuples.of(state.getT2(), state.getT1() + state.getT2());
                });
        List<Long> fibonacciSeries = new LinkedList<>();
        int size = 50;
        fibonacciGenerator.take(size).subscribe(t -> {
            fibonacciSeries.add(t);
        });
        System.out.println(fibonacciSeries);
        assertEquals( 7778742049L, fibonacciSeries.get(size-1).longValue());
    }

}
