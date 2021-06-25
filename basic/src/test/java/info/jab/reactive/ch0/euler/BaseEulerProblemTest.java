package info.jab.reactive.ch0.euler;

import org.junit.jupiter.api.BeforeEach;

public class BaseEulerProblemTest {

    protected AnswerLoader euler;

    @BeforeEach
    private void init() {
        euler = new AnswerLoader();
    }

}