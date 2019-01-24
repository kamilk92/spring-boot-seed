package pl.kkp.core.db.service.validate.exception;

import pl.kkp.core.db.service.validate.ValidatorActionType;

public class EntityNotExistException extends ValidationException {
    public static final String FAILURE_REASON_FMT = "Entity '%s' with given parameter '%s' doesn't exist.";

    public EntityNotExistException(ValidatorActionType action, String validatedField, String validatedParameter) {
        super(action, String.format(FAILURE_REASON_FMT, validatedField, validatedParameter));
    }
}
