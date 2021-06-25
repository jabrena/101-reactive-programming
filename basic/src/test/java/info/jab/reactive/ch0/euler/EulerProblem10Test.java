package info.jab.reactive.ch0.euler;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EulerProblem10Test extends BaseEulerProblemTest  {

    @Disabled
    @Test
    public void given_JavaSolution_when_executeMethod_then_expectedResultsTest() {

    }

    @Test
    public void given_JavaStreamSolution_when_executeMethod_then_expectedResultsTest() {

        EulerProblem10 problem = new EulerProblem10();

        assertThat(problem.JavaStreamSolution(10L)).isEqualTo(17);
        //Working but very slow in execution
        //assertThat(problem.javaStreamSolution(2_000_000L)).isEqualTo(euler.getAnswerToLong(10));
    }

    @Disabled
    @Test
    public void given_ReactorSolution_when_executeMethod_then_expectedResultsTest() {

    }
}