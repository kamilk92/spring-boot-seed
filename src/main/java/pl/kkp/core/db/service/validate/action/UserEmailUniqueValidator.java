package pl.kkp.core.db.service.validate.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kkp.core.db.entity.User;
import pl.kkp.core.db.repository.UserRepository;

@Component
public class UserEmailUniqueValidator extends UniqueValueValidator<User> {
    public static final String VALIDATED_FIELD = "email";

    @Autowired
    private UserRepository userRepository;

    public UserEmailUniqueValidator() {
        super(VALIDATED_FIELD);
    }

    @Override
    protected boolean isUniqueValue(User entity) {
        String email = entity.getEmail();
        User foundUser = userRepository.findByEmail(email);

        return foundUser == null;
    }
}
