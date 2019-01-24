package pl.kkp.core.db.service.validate.action;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import pl.kkp.core.db.entity.User;

@Component
public class UserPasswordLengthValidator extends FieldLengthValidator<User> {
    public static final int MIN_PASSWORD_LENGTH = 5;
    public static final int MAX_PASSWORD_LENGTH = 20;
    public static final String VALIDATED_FIELD = "user.password";

    public UserPasswordLengthValidator() {
        super(VALIDATED_FIELD, MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH);
    }

    @Override
    public int getFieldLength(User entity) {
        String password = entity.getPassword();

        return StringUtils.length(password);
    }
}
