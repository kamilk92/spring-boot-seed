package pl.kkp.core.testing.mocks;

import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.action.FieldLengthValidator;
import pl.kkp.core.db.service.validate.exception.FieldLengthTooLongException;
import pl.kkp.core.db.service.validate.exception.FieldLengthTooShortException;
import pl.kkp.core.db.service.validate.exception.ValidationException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class FieldLengthServiceValidatorMocks {
    public static <T> void mockDoCallRealFieldLenValidateMethod(
            FieldLengthValidator<T> validator, T entity, ValidatorActionType action, String validatedField, int minLen,
            int maxLen) throws ValidationException {
        ServiceValidatorMocks.mockDoCallRealValidateMethod(validator, entity, action);
        when(validator.getValidatedField()).thenReturn(validatedField);
        when(validator.getFieldLength(any())).thenCallRealMethod();
        when(validator.getMinFieldLengthInclusive()).thenReturn(minLen);
        when(validator.getMaxFieldLengthInclusive()).thenReturn(maxLen);
    }

    public static String buildFieldTooLongValidationMessage(
            ValidatorActionType action, String fieldName, int actualLen, int maxLen) {
        String failureReason = String.format(
                FieldLengthTooLongException.FAILURE_REASON_FMT, fieldName, actualLen, maxLen);

        return ServiceValidatorMocks.buildValidationMessage(action, failureReason);
    }

    public static String buildFieldTooShortValidationMessage(
            ValidatorActionType action, String fieldName, int actualLen, int maxLen) {
        String failureReason = String.format(
                FieldLengthTooShortException.FAILURE_REASON_FMT, fieldName, actualLen, maxLen);

        return ServiceValidatorMocks.buildValidationMessage(action, failureReason);
    }
}
