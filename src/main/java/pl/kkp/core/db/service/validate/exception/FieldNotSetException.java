package pl.kkp.core.db.service.validate.exception;

import pl.kkp.core.db.service.validate.ValidatorActionType;

public class FieldNotSetException extends ValidationException {
    public static final String FAILURE_REASON_FMT = "Field '%s' not set.";

    public FieldNotSetException(ValidatorActionType action, String fieldName) {
        super(action, String.format(FAILURE_REASON_FMT, fieldName));
    }
}
