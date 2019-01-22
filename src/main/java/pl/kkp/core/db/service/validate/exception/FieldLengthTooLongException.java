package pl.kkp.core.db.service.validate.exception;

import pl.kkp.core.db.service.validate.ValidatorActionType;

public class FieldLengthTooLongException extends ValidationException {
    public static final String FAILURE_REASON_FMT = "Field '%s' length %d too big, maximum field length value is %d";

    public FieldLengthTooLongException(
            ValidatorActionType action, String validatedFieldName, int actualLen, int maxLen) {
        super(action, String.format(FAILURE_REASON_FMT, validatedFieldName, actualLen, maxLen));
    }
}
