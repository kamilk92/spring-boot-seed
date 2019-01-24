package pl.kkp.core.db.service.validate.action;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.kkp.core.db.entity.User;
import pl.kkp.core.db.repository.UserRepository;
import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.exception.NotUniqueValueException;
import pl.kkp.core.db.service.validate.exception.ValidationException;
import pl.kkp.core.testing.SpringBootBaseTest;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.Mockito.when;
import static pl.kkp.core.testing.asserations.ExceptionAssertions.assertExceptionMessage;
import static pl.kkp.core.testing.mocks.UniqueValueServiceValidatorMocks.buildUniqueValueValidationMessage;

public class TestUserLoginUniqueValidator extends SpringBootBaseTest {

    private User user;

    @Autowired
    private UserLoginUniqueValidator userLoginUniqueValidator;

    @MockBean
    private UserRepository userRepository;

    @Before
    public void setUp() {
        user = setUpUser();
    }

    @Test
    public void isPassWhenUserLoginUnique() throws ValidationException {
        String userLogin = user.getLogin();
        when(userRepository.findByLogin(userLogin)).thenReturn(null);
        ValidatorActionType action = ValidatorActionType.SAVE;

        userLoginUniqueValidator.validate(user, action);
    }

    @Test
    public void isRaiseWhenUserLoginNotUnique() {
        String userLogin = user.getLogin();
        when(userRepository.findByLogin(userLogin)).thenReturn(user);
        ValidatorActionType action = ValidatorActionType.SAVE;

        Throwable thrown = catchThrowable(() -> {
            userLoginUniqueValidator.validate(user, action);
        });

        String expectedMessage = buildUniqueValueValidationMessage(action, UserLoginUniqueValidator.VALIDATED_FIELD);
        assertExceptionMessage(expectedMessage, NotUniqueValueException.class, thrown);
    }

    private User setUpUser() {
        String userLogin = "login";
        return new User(userLogin);
    }
}
