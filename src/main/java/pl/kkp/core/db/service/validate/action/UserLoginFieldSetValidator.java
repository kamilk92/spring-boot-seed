package pl.kkp.core.db.service.validate.action;

import org.springframework.stereotype.Component;
import pl.kkp.core.db.entity.User;

@Component
public class UserLoginFieldSetValidator extends FieldSetValidator<User> {
    public static final String VALIDATED_FIELD = "login";

    public UserLoginFieldSetValidator() {
        super(VALIDATED_FIELD);
    }

    @Override
    public boolean isFieldSet(User entity) {
        return isStringNotEmpty(entity.getLogin());
    }
}
