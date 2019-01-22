package pl.kkp.core.db.service.validate.exception;

import pl.kkp.core.db.service.validate.ValidatorActionType;

public class FieldLengthTooShortException extends ValidationException {
    public static final String FAILURE_REASON_FMT = "Field '%s' length %d too small, minimum field length value is %d";

    public FieldLengthTooShortException(
            ValidatorActionType action, String validatedFieldName, int actualLen, int minLen) {
        super(action, String.format(FAILURE_REASON_FMT, validatedFieldName, actualLen, minLen));
    }
}
