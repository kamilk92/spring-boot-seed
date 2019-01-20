package pl.kkp.core.db.service.validate.exception;

import pl.kkp.core.db.service.exception.EntityServiceException;
import pl.kkp.core.db.service.validate.ValidatorActionType;

public class ValidationException extends EntityServiceException {
    public static final String EXCEPTION_MESSAGE = "Can't perform action %s. Reason: %s";

    public ValidationException(ValidatorActionType action, String failureReason) {
        super(String.format(EXCEPTION_MESSAGE, action.toString(), failureReason));
    }
}
