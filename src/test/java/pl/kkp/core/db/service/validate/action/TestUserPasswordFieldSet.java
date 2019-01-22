package pl.kkp.core.db.service.validate.action;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kkp.core.db.entity.User;
import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.exception.FieldNotSetException;
import pl.kkp.core.db.service.validate.exception.ValidationException;
import pl.kkp.core.testing.SpringBootBaseTest;
import pl.kkp.core.testing.mocks.FieldSetServiceValidatorMocks;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static pl.kkp.core.testing.asserations.ExceptionAssertaions.assertExceptionMessage;
import static pl.kkp.core.testing.mocks.FieldSetServiceValidatorMocks.buildFiledNotSetValidationMessage;

public class TestUserPasswordFieldSet extends SpringBootBaseTest {
    @Autowired
    private UserPasswordFieldSet userPasswordFieldSet;

    @Test
    public void isPassWhenPasswordFieldSet() throws ValidationException {
        User user = new User();
        String userPassword = "password";
        user.setPassword(userPassword);
        ValidatorActionType action = ValidatorActionType.SAVE;

        userPasswordFieldSet.validate(user, action);
    }

    @Test
    public void isThrowExceptionWhenPasswordFieldNotSet() {
        User user = new User();
        ValidatorActionType action = ValidatorActionType.SAVE;

        Throwable thrown = catchThrowable(() -> {
            userPasswordFieldSet.validate(user, action);
        });

        String expectedMsg = buildFiledNotSetValidationMessage(action, UserPasswordFieldSet.VALIDATED_FIELD);
        assertExceptionMessage(expectedMsg, FieldNotSetException.class, thrown);
    }
}
