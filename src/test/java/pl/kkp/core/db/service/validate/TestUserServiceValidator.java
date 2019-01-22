package pl.kkp.core.db.service.validate;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.kkp.core.db.entity.User;
import pl.kkp.core.db.service.validate.action.UserLoginFieldSet;
import pl.kkp.core.db.service.validate.action.UserPasswordFieldSet;
import pl.kkp.core.db.service.validate.exception.FieldNotSetException;
import pl.kkp.core.db.service.validate.exception.ValidationException;
import pl.kkp.core.testing.SpringBootBaseTest;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static pl.kkp.core.testing.asserations.ExceptionAssertaions.assertExceptionMessage;
import static pl.kkp.core.testing.mocks.FieldSetServiceValidatorMocks.buildFiledNotSetValidationMessage;
import static pl.kkp.core.testing.mocks.FieldSetServiceValidatorMocks.mockDoCallRealFieldValidateMethod;
import static pl.kkp.core.testing.mocks.ServiceValidatorMocks.mockDoNothingOnValidateMethod;

public class TestUserServiceValidator extends SpringBootBaseTest {
    @Autowired
    private ServiceValidator<User> userServiceValidator;

    @MockBean
    private UserLoginFieldSet userLoginFieldSet;

    @MockBean
    private UserPasswordFieldSet userPasswordFieldSet;

    @Test
    public void testPassWhenUserLoginSetOnSaveAction() throws ValidationException {
        String login = "user";
        User user = new User(login);
        ValidatorActionType action = ValidatorActionType.SAVE;

        userServiceValidator.validate(user, action);
    }

    @Test
    public void isRaiseExceptionWhenUserLoginNotSetOnSaveAction() throws ValidationException {
        User user = new User();
        ValidatorActionType action = ValidatorActionType.SAVE;
        String validatedField = UserLoginFieldSet.VALIDATED_FIELD_NAME;

        mockDoNothingOnValidateMethod(userPasswordFieldSet, user, action);
        mockDoCallRealFieldValidateMethod(userLoginFieldSet, user, action, validatedField);

        Throwable thrown = catchThrowable(() -> {
            userServiceValidator.validate(user, action);
        });

        String expectedMsg = buildFiledNotSetValidationMessage(action, validatedField);
        assertExceptionMessage(expectedMsg, FieldNotSetException.class, thrown);
    }

    @Test
    public void isRaiseExceptionWhenUserPasswordNotSetOnSaveAction() throws ValidationException {
        String login = "login";
        User user = new User(login);
        ValidatorActionType action = ValidatorActionType.SAVE;
        String validatedField = UserPasswordFieldSet.VALIDATED_FIELD;

        mockDoNothingOnValidateMethod(userLoginFieldSet, user, action);
        mockDoCallRealFieldValidateMethod(userPasswordFieldSet, user, action, validatedField);

        Throwable thrown = catchThrowable(() -> {
            userServiceValidator.validate(user, action);
        });

        String expectedMsg = buildFiledNotSetValidationMessage(action, validatedField);
        assertExceptionMessage(expectedMsg, FieldNotSetException.class, thrown);
    }
}
