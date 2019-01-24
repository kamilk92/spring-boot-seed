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

public class TestUserEmailUniqueValidator extends SpringBootBaseTest {

    private User user;

    @Autowired
    private UserEmailUniqueValidator userEmailUniqueValidator;

    @MockBean
    private UserRepository userRepository;

    @Before
    public void setUp() {
        user = setUpUser();
    }

    @Test
    public void isPassWhenUserEmailUnique() throws ValidationException {
        String userLogin = user.getLogin();
        when(userRepository.findByLogin(userLogin)).thenReturn(null);
        ValidatorActionType action = ValidatorActionType.SAVE;

        userEmailUniqueValidator.validate(user, action);
    }

    @Test
    public void isRaiseExceptionUserEmailIsNotUnique() {
        String userEmail = user.getEmail();
        when(userRepository.findByEmail(userEmail)).thenReturn(user);
        ValidatorActionType action = ValidatorActionType.SAVE;

        Throwable thrown = catchThrowable(() -> {
            userEmailUniqueValidator.validate(user, action);
        });

        String expectedMessage = buildUniqueValueValidationMessage(action, UserEmailUniqueValidator.VALIDATED_FIELD);
        assertExceptionMessage(expectedMessage, NotUniqueValueException.class, thrown);
    }

    private User setUpUser() {
        User user = new User();
        user.setLogin("login");

        return user;
    }

}
