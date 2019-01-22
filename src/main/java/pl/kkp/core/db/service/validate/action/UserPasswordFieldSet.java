package pl.kkp.core.db.service.validate.action;

import org.springframework.stereotype.Component;
import pl.kkp.core.db.entity.User;

@Component
public class UserPasswordFieldSet extends FieldSetValidator<User> {
    public static final String VALIDATED_FIELD = "password";

    public UserPasswordFieldSet() {
        super(VALIDATED_FIELD);
    }

    @Override
    protected boolean isFieldSet(User entity) {
        return isStringNotEmpty(entity.getPassword());
    }
}
