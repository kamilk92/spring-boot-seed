package pl.kkp.core.testing.mocks;

import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.exception.NotUniqueValueException;

public class UniqueValueServiceValidatorMocks {
    public static String buildUniqueValueValidationMessage(ValidatorActionType action, String fieldName) {
        String failureReason = String.format(NotUniqueValueException.FAILURE_REASON_FMT, fieldName);

        return ServiceValidatorMocks.buildValidationMessage(action, failureReason);
    }
}
