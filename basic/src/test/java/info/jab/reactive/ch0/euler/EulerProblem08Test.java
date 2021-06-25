package info.jab.reactive.ch0.euler;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EulerProblem08Test extends BaseEulerProblemTest {

    @Test
    public void given_JavaSolution_when_executeMethod_then_expectedResultsTest() {

        EulerProblem08 problem = new EulerProblem08();

        assertThat(problem.JavaSolution(4)).isEqualTo(5_832);
        assertThat(problem.JavaSolution(13)).isEqualTo(euler.getAnswerToLong(8));
    }

    @Test
    public void given_JavaStreamSolution_when_executeMethod_then_expectedResultsTest() {

        EulerProblem08 problem = new EulerProblem08();

        assertThat(problem.JavaStreamSolution(4)).isEqualTo(5_832);
        assertThat(problem.JavaStreamSolution(13)).isEqualTo(euler.getAnswerToLong(8));
    }

    @Disabled
    @Test
    public void given_ReactorSolution_when_executeMethod_then_expectedResultsTest() {

    }

}