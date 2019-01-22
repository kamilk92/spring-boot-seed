package pl.kkp.core.db.service.validate.action;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kkp.core.db.entity.User;
import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.exception.ValidationException;
import pl.kkp.core.testing.SpringBootBaseTest;

public class TestPasswordFieldLengthValidator extends SpringBootBaseTest {
    @Autowired
    private UserPasswordLengthValidator userPasswordLengthValidator;

    @Test
    public void isPassWhenUserPasswordLengthIsValid() throws ValidationException {
        User user = new User();
        user.setPassword("secret");

        userPasswordLengthValidator.validate(user, ValidatorActionType.SAVE);
    }
}
