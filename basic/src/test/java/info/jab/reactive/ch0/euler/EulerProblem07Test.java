package info.jab.reactive.ch0.euler;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EulerProblem07Test extends BaseEulerProblemTest {

    @Disabled
    @Test
    public void given_JavaSolution_when_executeMethod_then_expectedResultsTest() {

    }

    @Test
    public void given_JavaStreamSolution_when_executeMethod_then_expectedResultsTest() {

        EulerProblem07 problem7 = new EulerProblem07();

        assertThat(problem7.isPrime(7)).isEqualTo(true);
        assertThat(problem7.isPrime(9)).isEqualTo(false);
        assertThat(problem7.JavaStreamSolution(6L)).isEqualTo(13);
        assertThat(problem7.JavaStreamSolution(10001)).isEqualTo(euler.getAnswerToLong(7));

    }

    @Disabled
    @Test
    public void given_ReactorSolution_when_executeMethod_then_expectedResultsTest() {

    }
}