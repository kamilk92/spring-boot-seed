package pl.kkp.core.db.service.validate.exception;

import org.junit.Test;
import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.testing.mocks.ServiceValidatorMocks;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

public class TestValidationExceptionMessage {
    @Test
    public void isRaiseWithExpectedMessage() {
        ValidatorActionType validatorAction = ValidatorActionType.SAVE;
        String failureReason = "Field not set.";

        Throwable thrown = catchThrowable(() -> {
            throw new ValidationException(validatorAction, failureReason);
        });

        String expectedMessage = ServiceValidatorMocks.buildValidationMessage(
                ValidatorActionType.SAVE, failureReason);
        assertThat(thrown)
                .isInstanceOf(ValidationException.class)
                .hasMessage(expectedMessage);
    }
}
