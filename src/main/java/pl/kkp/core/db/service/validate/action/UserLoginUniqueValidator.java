package pl.kkp.core.db.service.validate.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import pl.kkp.core.db.entity.User;
import pl.kkp.core.db.service.UserService;

@Component
public class UserLoginUniqueValidator extends UniqueValueValidator<User> {
    public static final String VALIDATED_FIELD = "user.login";

    @Autowired
    @Lazy
    private UserService userService;

    public UserLoginUniqueValidator() {
        super(VALIDATED_FIELD);
    }

    @Override
    protected boolean isUniqueValue(User entity) {
        String userLogin = entity.getLogin();
        User foundUser = userService.findByLogin(userLogin);

        return foundUser == null;
    }
}
