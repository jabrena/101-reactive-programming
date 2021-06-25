package info.jab.reactive.ch0.euler;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EulerProblem04Test extends BaseEulerProblemTest {

    @Disabled
    @Test
    public void given_JavaSolution_when_executeMethod_then_expectedResultsTest() {

        EulerProblem04 problem = new EulerProblem04();

        assertThat(problem.JavaSolution(10, 99)).isEqualTo(9009L);
        assertThat(problem.JavaSolution(100, 999)).isEqualTo(euler.getAnswerToLong(4));
    }

    @Test
    public void given_JavaStreamSolution_when_executeMethod_then_expectedResultsTest() {

        EulerProblem04 problem = new EulerProblem04();

        assertThat(problem.JavaStreamSolution(10, 99)).isEqualTo(9009);
        assertThat(problem.JavaStreamSolution(100, 999)).isEqualTo(euler.getAnswerToInt(4));
    }

    @Test
    public void given_ReactorSolution_when_executeMethod_then_expectedResultsTest() {

        EulerProblem04 problem = new EulerProblem04();

        StepVerifier
                .create(problem.ReactorSolution(10, 99))
                .expectNext(9009)
                .expectComplete()
                .verify();

        StepVerifier
                .create(problem.ReactorSolution(100, 999))
                .expectNext(euler.getAnswerToInt(4))
                .expectComplete()
                .verify();
    }

}