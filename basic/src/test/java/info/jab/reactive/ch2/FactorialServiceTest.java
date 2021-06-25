package info.jab.reactive.ch2;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FactorialServiceTest {

    @Test
    public void testFactorial() {
        Flux<Double> factorialGenerator = new FactorialService().generateFactorial(10);
        factorialGenerator
                .doOnNext(t -> System.out.println(t))
                .last()
                .subscribe(t -> assertEquals(3628800.0, t, 0.0));
    }

}
