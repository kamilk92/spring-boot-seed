package pl.kkp.core.testing.asserations;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ExceptionAssertaions {
    public static void assertExceptionMessage(String expectedMessage, Class<? extends Exception> targetException, Throwable thrown) {
        assertThat(thrown)
                .isInstanceOf(targetException)
                .hasMessage(expectedMessage);
    }
}
