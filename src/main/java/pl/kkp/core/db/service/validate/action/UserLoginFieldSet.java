package pl.kkp.core.db.service.validate.action;

import org.springframework.stereotype.Component;
import pl.kkp.core.db.entity.User;

@Component
public class UserLoginFieldSet extends FieldSetValidator<User> {
    public static final String VALIDATED_FIELD_NAME = "login";

    public UserLoginFieldSet() {
        super(VALIDATED_FIELD_NAME);
    }

    @Override
    protected boolean isFieldSet(User entity) {
        return isStringNotEmpty(entity.getLogin());
    }
}
