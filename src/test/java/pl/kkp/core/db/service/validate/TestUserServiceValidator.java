package pl.kkp.core.db.service.validate;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.kkp.core.db.entity.User;
import pl.kkp.core.db.repository.UserRepository;
import pl.kkp.core.db.service.UserService;
import pl.kkp.core.db.service.validate.action.UserEmailFieldSetValidator;
import pl.kkp.core.db.service.validate.action.UserEmailUniqueValidator;
import pl.kkp.core.db.service.validate.action.UserLoginFieldSetValidator;
import pl.kkp.core.db.service.validate.action.UserLoginUniqueValidator;
import pl.kkp.core.db.service.validate.action.UserPasswordFieldSetValidator;
import pl.kkp.core.db.service.validate.action.UserPasswordLengthValidator;
import pl.kkp.core.db.service.validate.exception.FieldLengthTooLongException;
import pl.kkp.core.db.service.validate.exception.FieldLengthTooShortException;
import pl.kkp.core.db.service.validate.exception.FieldNotSetException;
import pl.kkp.core.db.service.validate.exception.NotUniqueValueException;
import pl.kkp.core.db.service.validate.exception.ValidationException;
import pl.kkp.core.testing.SpringBootBaseTest;
import pl.kkp.core.util.RandomStringGenerator;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.Mockito.when;
import static pl.kkp.core.testing.asserations.ExceptionAssertions.assertExceptionMessage;
import static pl.kkp.core.testing.mocks.FieldLengthServiceValidatorMocks.buildFieldTooLongValidationMessage;
import static pl.kkp.core.testing.mocks.FieldLengthServiceValidatorMocks.buildFieldTooShortValidationMessage;
import static pl.kkp.core.testing.mocks.FieldLengthServiceValidatorMocks.mockDoCallRealFieldLenValidateMethod;
import static pl.kkp.core.testing.mocks.FieldSetServiceValidatorMocks.buildFieldNotSetValidationMessage;
import static pl.kkp.core.testing.mocks.FieldSetServiceValidatorMocks.mockDoCallRealFieldValidateMethod;
import static pl.kkp.core.testing.mocks.ServiceValidatorMocks.mockDoNothingOnValidateMethod;
import static pl.kkp.core.testing.mocks.UniqueValueServiceValidatorMocks.buildUniqueValueValidationMessage;

public class TestUserServiceValidator extends SpringBootBaseTest {
    private RandomStringGenerator passwordGenerator;

    @Autowired
    private ServiceValidator<User> userServiceValidator;

    @MockBean
    private UserEmailUniqueValidator userEmailUniqueValidator;

    @MockBean
    private UserEmailFieldSetValidator userEmailFieldNotSetValidator;

    @MockBean
    private UserLoginFieldSetValidator userLoginFieldSetValidator;

    @MockBean
    private UserLoginUniqueValidator userLoginUniqueValidator;

    @MockBean
    private UserPasswordFieldSetValidator userPasswordFieldSetValidator;

    @MockBean
    private UserPasswordLengthValidator userPasswordLengthValidator;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserService userService;

    public TestUserServiceValidator() {
        passwordGenerator = new RandomStringGenerator(UserPasswordLengthValidator.MAX_PASSWORD_LENGTH + 1);
    }

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
        String validatedField = UserLoginFieldSetValidator.VALIDATED_FIELD;

        mockDoNothingOnValidateMethod(userEmailFieldNotSetValidator, user, action);
        mockDoCallRealFieldValidateMethod(userLoginFieldSetValidator, user, action, validatedField);
        mockDoNothingOnValidateMethod(userPasswordFieldSetValidator, user, action);
        mockDoNothingOnValidateMethod(userLoginUniqueValidator, user, action);
        mockDoNothingOnValidateMethod(userEmailUniqueValidator, user, action);
        mockDoNothingOnValidateMethod(userPasswordLengthValidator, user, action);

        Throwable thrown = catchThrowable(() -> {
            userServiceValidator.validate(user, action);
        });

        String expectedMsg = buildFieldNotSetValidationMessage(action, validatedField);
        assertExceptionMessage(expectedMsg, FieldNotSetException.class, thrown);
    }

    @Test
    public void isRaiseExceptionWhenUserPasswordNotSetOnSaveAction() throws ValidationException {
        String login = "getUserByLogin";
        User user = new User(login);
        ValidatorActionType action = ValidatorActionType.SAVE;
        String validatedField = UserPasswordFieldSetValidator.VALIDATED_FIELD;

        mockDoNothingOnValidateMethod(userEmailFieldNotSetValidator, user, action);
        mockDoNothingOnValidateMethod(userLoginFieldSetValidator, user, action);
        mockDoCallRealFieldValidateMethod(userPasswordFieldSetValidator, user, action, validatedField);
        mockDoNothingOnValidateMethod(userLoginUniqueValidator, user, action);
        mockDoNothingOnValidateMethod(userEmailUniqueValidator, user, action);
        mockDoNothingOnValidateMethod(userPasswordLengthValidator, user, action);

        Throwable thrown = catchThrowable(() -> {
            userServiceValidator.validate(user, action);
        });

        String expectedMsg = buildFieldNotSetValidationMessage(action, validatedField);
        assertExceptionMessage(expectedMsg, FieldNotSetException.class, thrown);
    }

    @Test
    public void isRaiseExceptionWhenUserLoginIsNotUnique() throws ValidationException {
        String login = "getUserByLogin";
        User user = new User(login);
        ValidatorActionType action = ValidatorActionType.SAVE;

        mockDoNothingOnValidateMethod(userEmailFieldNotSetValidator, user, action);
        mockDoNothingOnValidateMethod(userLoginFieldSetValidator, user, action);
        mockDoNothingOnValidateMethod(userPasswordFieldSetValidator, user, action);
        String validatedField = UserLoginUniqueValidator.VALIDATED_FIELD;
        mockDoCallRealFieldValidateMethod(userLoginUniqueValidator, user, action, validatedField);
        mockDoNothingOnValidateMethod(userEmailUniqueValidator, user, action);
        mockDoNothingOnValidateMethod(userPasswordLengthValidator, user, action);
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
        mockDoNothingOnValidateMethod(userEmailFieldNotSetValidator, user, action);
        mockDoNothingOnValidateMethod(userLoginFieldSetValidator, user, action);
        mockDoNothingOnValidateMethod(userPasswordFieldSetValidator, user, action);
        mockDoNothingOnValidateMethod(userLoginUniqueValidator, user, action);
        String validatedField = UserEmailUniqueValidator.VALIDATED_FIELD;
        mockDoCallRealFieldValidateMethod(userEmailUniqueValidator, user, action, validatedField);
        mockDoNothingOnValidateMethod(userPasswordLengthValidator, user, action);
        when(userRepository.findByEmail(userEmail)).thenReturn(user);

        Throwable thrown = catchThrowable(() -> {
            userServiceValidator.validate(user, action);
        });

        String expectedMessage = buildUniqueValueValidationMessage(action, validatedField);
        assertExceptionMessage(expectedMessage, NotUniqueValueException.class, thrown);
    }

    @Test
    public void isRaiseExceptionWhenUserPasswordLengthIsTooShort() throws ValidationException {
        User user = new User();
        String userPass = "se";
        user.setPassword(userPass);


        ValidatorActionType action = ValidatorActionType.SAVE;
        mockDoNothingOnValidateMethod(userEmailFieldNotSetValidator, user, action);
        mockDoNothingOnValidateMethod(userLoginFieldSetValidator, user, action);
        mockDoNothingOnValidateMethod(userPasswordFieldSetValidator, user, action);
        mockDoNothingOnValidateMethod(userLoginUniqueValidator, user, action);
        mockDoNothingOnValidateMethod(userEmailUniqueValidator, user, action);

        String validatedField = UserPasswordLengthValidator.VALIDATED_FIELD;
        int minPasswordLen = UserPasswordLengthValidator.MIN_PASSWORD_LENGTH;
        int maxPasswordLen = UserPasswordLengthValidator.MAX_PASSWORD_LENGTH;
        mockDoCallRealFieldLenValidateMethod(
                userPasswordLengthValidator, user, action, validatedField, minPasswordLen, maxPasswordLen);

        Throwable thrown = catchThrowable(() -> {
            userServiceValidator.validate(user, action);
        });

        String expectedMessage = buildFieldTooShortValidationMessage(
                action, validatedField, userPass.length(), minPasswordLen);
        assertExceptionMessage(expectedMessage, FieldLengthTooShortException.class, thrown);
    }

    @Test
    public void isRaiseExceptionWhenUserPasswordLengthIsTooLong() throws ValidationException {
        User user = new User();
        String userPass = passwordGenerator.generate();
        user.setPassword(userPass);

        ValidatorActionType action = ValidatorActionType.SAVE;
        mockDoNothingOnValidateMethod(userEmailFieldNotSetValidator, user, action);
        mockDoNothingOnValidateMethod(userLoginFieldSetValidator, user, action);
        mockDoNothingOnValidateMethod(userPasswordFieldSetValidator, user, action);
        mockDoNothingOnValidateMethod(userLoginUniqueValidator, user, action);
        mockDoNothingOnValidateMethod(userEmailUniqueValidator, user, action);

        String validatedField = UserPasswordLengthValidator.VALIDATED_FIELD;
        int minPasswordLen = UserPasswordLengthValidator.MIN_PASSWORD_LENGTH;
        int maxPasswordLen = UserPasswordLengthValidator.MAX_PASSWORD_LENGTH;
        mockDoCallRealFieldLenValidateMethod(
                userPasswordLengthValidator, user, action, validatedField, minPasswordLen, maxPasswordLen);

        Throwable thrown = catchThrowable(() -> {
            userServiceValidator.validate(user, action);
        });

        int actualPassLen = userPass.length();
        String expectedMessage = buildFieldTooLongValidationMessage(
                action, userPasswordLengthValidator, actualPassLen);
        assertExceptionMessage(expectedMessage, FieldLengthTooLongException.class, thrown);
    }
}
