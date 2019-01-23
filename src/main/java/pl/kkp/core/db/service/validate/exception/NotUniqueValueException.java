package pl.kkp.core.db.service.validate.exception;

import pl.kkp.core.db.service.validate.ValidatorActionType;

public class NotUniqueValueException extends ValidationException {
    public static final String FAILURE_REASON_FMT = "Field '%s' value not unique.";

    public NotUniqueValueException(ValidatorActionType action, String validatedField) {
        super(action, String.format(FAILURE_REASON_FMT, validatedField));
    }
}
