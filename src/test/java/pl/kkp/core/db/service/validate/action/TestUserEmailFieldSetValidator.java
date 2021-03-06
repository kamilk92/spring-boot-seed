package pl.kkp.core.db.service.validate.action;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kkp.core.db.entity.User;
import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.exception.FieldNotSetException;
import pl.kkp.core.db.service.validate.exception.ValidationException;
import pl.kkp.core.testing.SpringBootBaseTest;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static pl.kkp.core.testing.asserations.ExceptionAssertions.assertExceptionMessage;
import static pl.kkp.core.testing.mocks.FieldSetServiceValidatorMocks.buildFieldNotSetValidationMessage;

public class TestUserEmailFieldSetValidator extends SpringBootBaseTest {

    @Autowired
    private UserEmailFieldSetValidator userEmailFieldSet;

    @Test
    public void isPassWhenEmailFieldSet() throws ValidationException {
        User user = new User();
        user.setEmail("user@domain.com");
        ValidatorActionType action = ValidatorActionType.SAVE;

        userEmailFieldSet.validate(user, action);
    }

    @Test
    public void isRaiseExceptionWhenEmailFieldNotSet() {
        User user = new User();
        ValidatorActionType action = ValidatorActionType.SAVE;

        Throwable thrown = catchThrowable(() -> {
            userEmailFieldSet.validate(user, action);
        });

        String expectedMessage = buildFieldNotSetValidationMessage(
                action, UserEmailFieldSetValidator.VALIDATED_FIELD);
        assertExceptionMessage(expectedMessage, FieldNotSetException.class, thrown);
    }
}
