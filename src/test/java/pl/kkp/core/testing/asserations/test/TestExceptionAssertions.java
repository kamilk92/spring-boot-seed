package pl.kkp.core.testing.asserations.test;

import org.junit.Before;
import org.junit.Test;
import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.exception.ValidationException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static pl.kkp.core.testing.asserations.ExceptionAssertions.assertExceptionMessage;

public class TestExceptionAssertions {
    private static final ValidatorActionType ACTION_TYPE = ValidatorActionType.SAVE;
    private static final String FAILURE_REASON = "Missing field.";

    private ValidationException raisedException;

    @Before
    public void setUp() {
        raisedException = new ValidationException(ACTION_TYPE, FAILURE_REASON);
    }

    @Test
    public void isPassWhenExpectedMessageIsCorrect() {
        String expectedMessage = String.format(ValidationException.EXCEPTION_MESSAGE, ACTION_TYPE, FAILURE_REASON);

        assertExceptionMessage(expectedMessage, ValidationException.class, raisedException);
    }

    @Test
    public void isFailureWhenExpectedMessageIsIncorrrect() {
        String failureReason = FAILURE_REASON.concat("Extra reason.");
        String expectedMessage = String.format(ValidationException.EXCEPTION_MESSAGE, ACTION_TYPE, failureReason);

        Throwable thrown = catchThrowable(() -> {
            assertExceptionMessage(expectedMessage, ValidationException.class, raisedException);
        });

        assertThat(thrown).isInstanceOf(AssertionError.class);
    }
}
