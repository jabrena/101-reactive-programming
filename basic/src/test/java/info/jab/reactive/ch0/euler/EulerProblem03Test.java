package info.jab.reactive.ch0.euler;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EulerProblem03Test extends BaseEulerProblemTest {

    @Test
    public void given_JavaSolution_when_executeMethod_then_expectedResultsTest() {

        EulerProblem03 problem = new EulerProblem03();

        assertThat(problem.JavaSolution(13195L)).isEqualTo(29);
        assertThat(problem.JavaSolution(600851475143L)).isEqualTo(euler.getAnswerToLong(3));
    }

    @Test
    public void given_JavaStreamSolution_when_executeMethod_then_expectedResultsTest() {

        EulerProblem03 problem = new EulerProblem03();

        assertThat(problem.JavaStreamSolution(13195L)).isEqualTo(29);
        assertThat(problem.JavaStreamSolution(600851475143L)).isEqualTo(euler.getAnswerToLong(3));
    }


    @Disabled
    @Test
    public void given_ReactorSolution_when_executeMethod_then_expectedResultsTest() {

        EulerProblem03 problem = new EulerProblem03();

        StepVerifier
                .create(problem.ReactorSolution(13195L))
                .expectNext(29L)
                .expectComplete()
                .verify();

        StepVerifier
                .create(problem.ReactorSolution(600851475143L))
                .expectNext(euler.getAnswerToLong(3))
                .expectComplete()
                .verify();
    }

}