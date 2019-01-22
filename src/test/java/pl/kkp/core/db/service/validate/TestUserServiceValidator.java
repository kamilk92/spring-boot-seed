package pl.kkp.core.db.service.validate;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.kkp.core.db.entity.User;
import pl.kkp.core.db.repository.UserRepository;
import pl.kkp.core.db.service.UserService;
import pl.kkp.core.db.service.validate.action.UserEmailUniqueValidator;
import pl.kkp.core.db.service.validate.action.UserLoginFieldSetValidator;
import pl.kkp.core.db.service.validate.action.UserLoginUniqueValidator;
import pl.kkp.core.db.service.validate.action.UserPasswordFieldSetValidator;
import pl.kkp.core.db.service.validate.exception.FieldNotSetException;
import pl.kkp.core.db.service.validate.exception.NotUniqueValueException;
import pl.kkp.core.db.service.validate.exception.ValidationException;
import pl.kkp.core.testing.SpringBootBaseTest;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.Mockito.when;
import static pl.kkp.core.testing.asserations.ExceptionAssertaions.assertExceptionMessage;
import static pl.kkp.core.testing.mocks.FieldSetServiceValidatorMocks.buildFiledNotSetValidationMessage;
import static pl.kkp.core.testing.mocks.FieldSetServiceValidatorMocks.mockDoCallRealFieldValidateMethod;
import static pl.kkp.core.testing.mocks.ServiceValidatorMocks.mockDoNothingOnValidateMethod;
import static pl.kkp.core.testing.mocks.UniqueValueServiceValidatorMocks.buildUniqueValueValidationMessage;

public class TestUserServiceValidator extends SpringBootBaseTest {
    @Autowired
    private ServiceValidator<User> userServiceValidator;

    @MockBean
    private UserEmailUniqueValidator userEmailUniqueValidator;

    @MockBean
    private UserLoginFieldSetValidator userLoginFieldSetValidator;

    @MockBean
    private UserLoginUniqueValidator userLoginUniqueValidator;

    @MockBean
    private UserPasswordFieldSetValidator userPasswordFieldSetValidator;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserService userService;

    @Test
    public void isPassWhenUserLoginSetOnSaveAction() throws ValidationException {
        String login = "user";
        User user = new User(login);
        ValidatorActionType action = ValidatorActionType.SAVE;

        userServiceValidator.validate(user, action);
    }

    @Test
    public void isRaiseExceptionWhenUserLoginNotSetOnSaveAction() throws ValidationException {
        User user = new User();
        ValidatorActionType action = ValidatorActionType.SAVE;
        String validatedField = UserLoginFieldSetValidator.VALIDATED_FIELD_NAME;

        mockDoCallRealFieldValidateMethod(userLoginFieldSetValidator, user, action, validatedField);
        mockDoNothingOnValidateMethod(userPasswordFieldSetValidator, user, action);
        mockDoNothingOnValidateMethod(userLoginUniqueValidator, user, action);
        mockDoNothingOnValidateMethod(userEmailUniqueValidator, user, action);

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
        String validatedField = UserPasswordFieldSetValidator.VALIDATED_FIELD;

        mockDoNothingOnValidateMethod(userLoginFieldSetValidator, user, action);
        mockDoCallRealFieldValidateMethod(userPasswordFieldSetValidator, user, action, validatedField);
        mockDoNothingOnValidateMethod(userLoginUniqueValidator, user, action);
        mockDoNothingOnValidateMethod(userEmailUniqueValidator, user, action);

        Throwable thrown = catchThrowable(() -> {
            userServiceValidator.validate(user, action);
        });

        String expectedMsg = buildFiledNotSetValidationMessage(action, validatedField);
        assertExceptionMessage(expectedMsg, FieldNotSetException.class, thrown);
    }

    @Test
    public void isRaiseExceptionWhenUserLoginIsNotUnique() throws ValidationException {
        String login  = "login";
        User user = new User(login);
        ValidatorActionType action = ValidatorActionType.SAVE;
        String validatedField = UserLoginUniqueValidator.VALIDATED_FIELD;

        mockDoNothingOnValidateMethod(userLoginFieldSetValidator, user, action);
        mockDoNothingOnValidateMethod(userPasswordFieldSetValidator, user, action);
        mockDoCallRealFieldValidateMethod(userLoginUniqueValidator, user, action, validatedField);
        mockDoNothingOnValidateMethod(userEmailUniqueValidator, user, action);
        when(userService.findByLogin(login)).thenReturn(user);

        Throwable thrown = catchThrowable(() -> {
            userServiceValidator.validate(user, action);
        });

        String expectedMessage = buildUniqueValueValidationMessage(action, validatedField);
        assertExceptionMessage(expectedMessage, NotUniqueValueException.class, thrown);
    }

    @Test
    public void isRaiseExceptionWhenUserEmailIsNotUnique() throws ValidationException {
        User user = new User();
        String userEmail = "user@domain.pl";
        user.setEmail(userEmail);

        ValidatorActionType action = ValidatorActionType.SAVE;
        String validatedField = UserEmailUniqueValidator.VALIDATED_FIELD;

        mockDoNothingOnValidateMethod(userLoginFieldSetValidator, user, action);
        mockDoNothingOnValidateMethod(userPasswordFieldSetValidator, user, action);
        mockDoNothingOnValidateMethod(userLoginUniqueValidator, user, action);
        mockDoCallRealFieldValidateMethod(userEmailUniqueValidator, user, action, validatedField);
        when(userRepository.findByEmail(userEmail)).thenReturn(user);

        Throwable thrown = catchThrowable(() -> {
            userServiceValidator.validate(user, action);
        });

        String expectedMessage = buildUniqueValueValidationMessage(action, validatedField);
        assertExceptionMessage(expectedMessage, NotUniqueValueException.class, thrown);
    }
}
