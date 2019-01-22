package pl.kkp.core.db.service.validate.action;

import org.springframework.stereotype.Component;
import pl.kkp.core.db.entity.User;

@Component
public class UserEmailFieldSet extends FieldSetValidator<User> {
    public static final String VALIDATED_FIELD_NAME = "email";

    public UserEmailFieldSet() {
        super(VALIDATED_FIELD_NAME);
    }

    @Override
    protected boolean isFieldSet(User entity) {
        return isStringNotEmpty(entity.getEmail());
    }
}
