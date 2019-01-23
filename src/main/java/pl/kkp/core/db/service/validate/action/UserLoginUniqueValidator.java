package pl.kkp.core.db.service.validate.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kkp.core.db.entity.User;
import pl.kkp.core.db.repository.UserRepository;

@Component
public class UserLoginUniqueValidator extends UniqueValueValidator<User> {
    public static final String VALIDATED_FIELD = "user.login";

    @Autowired
    private UserRepository userRepository;

    public UserLoginUniqueValidator() {
        super(VALIDATED_FIELD);
    }

    @Override
    public boolean isUniqueValue(User entity) {
        String userLogin = entity.getLogin();
        User foundUser = userRepository.findByLogin(userLogin);

        return foundUser == null;
    }
}
