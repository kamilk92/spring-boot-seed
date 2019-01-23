package pl.kkp.core.testing.mocks;

import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.action.UniqueValueValidator;
import pl.kkp.core.db.service.validate.exception.NotUniqueValueException;
import pl.kkp.core.db.service.validate.exception.ValidationException;

import static org.mockito.Mockito.when;
import static pl.kkp.core.testing.mocks.FieldSetServiceValidatorMocks.mockDoCallRealFieldValidateMethod;
import static pl.kkp.core.testing.mocks.ServiceValidatorMocks.buildValidationMessage;

public class UniqueValueServiceValidatorMocks {
    public static String buildUniqueValueValidationMessage(ValidatorActionType action, String fieldName) {
        String failureReason = String.format(NotUniqueValueException.FAILURE_REASON_FMT, fieldName);

        return buildValidationMessage(action, failureReason);
    }

    public static <T> void mockDoCallIsUniqueValidateMethod(
            UniqueValueValidator<T> validator,
            T entity,
            ValidatorActionType action,
            String validatedField,
            boolean isUniqueValue
    ) throws ValidationException {
        mockDoCallRealFieldValidateMethod(validator, entity, action, validatedField);
        when(validator.isUniqueValue(entity)).thenReturn(isUniqueValue);
    }
}
