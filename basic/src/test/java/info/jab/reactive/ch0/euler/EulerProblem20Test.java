package info.jab.reactive.ch0.euler;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;


public class EulerProblem20Test extends BaseEulerProblemTest {

    @Test
    public void given_ReactorSolution_when_executeMethod_then_expectedResultsTest() {

        EulerProblem20 problem = new EulerProblem20();

        StepVerifier
                .create(problem.ReactorSolution(10L))
                .expectNext(27L)
                .expectComplete()
                .verify();

        StepVerifier
                .create(problem.ReactorSolution(100L))
                .expectNext(euler.getAnswerToLong(20))
                .expectComplete()
                .verify();
    }

}