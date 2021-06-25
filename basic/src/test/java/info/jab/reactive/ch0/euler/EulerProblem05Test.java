package info.jab.reactive.ch0.euler;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EulerProblem05Test extends BaseEulerProblemTest {


    @Disabled
    @Test
    public void given_JavaSolution_when_executeMethod_then_expectedResultsTest() {

    }

    @Test
    public void given_JavaStreamSolution_when_executeMethod_then_expectedResultsTest(){

        EulerProblem05 problem = new EulerProblem05();

        assertThat(problem.JavaStreamSolution(10L)).isEqualTo(2_520L);
    }

    @Disabled
    @Test
    public void given_ReactorSolution_when_executeMethod_then_expectedResultsTest() {

        EulerProblem05 problem = new EulerProblem05();

        StepVerifier
                .create(problem.ReactorSolution(10L))
                .expectNext(2_520L)
                .expectComplete()
                .verify();

    }

}