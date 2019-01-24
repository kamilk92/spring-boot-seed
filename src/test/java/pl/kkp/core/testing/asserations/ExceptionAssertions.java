package pl.kkp.core.testing.asserations;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ExceptionAssertions {
    public static void assertExceptionMessage(
            String expectedMessage, Class<? extends Exception> targetException, Throwable thrown) {

        assertThat(thrown)
                .isInstanceOf(targetException)
                .hasMessage(expectedMessage);
    }
}
