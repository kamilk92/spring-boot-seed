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

public class TestUserLoginFieldSetValidator extends SpringBootBaseTest {

    @Autowired
    private UserLoginFieldSetValidator userLoginFieldSet;

    @Test
    public void isThrowExceptionWhenUserLoginNotSet() {
        User user = new User();
        ValidatorActionType action = ValidatorActionType.SAVE;

        Throwable thrown = catchThrowable(() -> {
            userLoginFieldSet.validate(user, action);
        });

        String expectedMessage = buildFieldNotSetValidationMessage(
                action, UserLoginFieldSetValidator.VALIDATED_FIELD);
        assertExceptionMessage(expectedMessage, FieldNotSetException.class, thrown);
    }

    @Test
    public void isPassWhenUserLoginSet() throws ValidationException {
        User user = new User();
        String login = "getUserByLogin";
        user.setLogin(login);
        ValidatorActionType action = ValidatorActionType.SAVE;

        userLoginFieldSet.validate(user, action);
    }
}
