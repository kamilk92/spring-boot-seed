package pl.kkp.core.testing.mocks;

import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.action.FieldSetValidator;
import pl.kkp.core.db.service.validate.action.FieldValidatorAction;
import pl.kkp.core.db.service.validate.exception.FieldNotSetException;
import pl.kkp.core.db.service.validate.exception.ValidationException;

import static org.mockito.Mockito.when;
import static pl.kkp.core.testing.mocks.ServiceValidatorMocks.mockDoCallRealValidateMethod;

public class FieldSetServiceValidatorMocks {
    public static String buildFiledNotSetValidationMessage(ValidatorActionType action, String fieldName) {
        String failureReason = String.format(FieldNotSetException.FAILURE_REASON_FMT, fieldName);

        return ServiceValidatorMocks.buildValidationMessage(action, failureReason);
    }

    public static <T> void mockDoCallIsFieldSetValidateMethod(
            FieldSetValidator<T> validator,
            T entity,
            ValidatorActionType action,
            String validatedField,
            boolean isFieldSet
    ) throws ValidationException {
        mockDoCallRealFieldValidateMethod(validator, entity, action, validatedField);
        when(validator.isFieldSet(entity)).thenReturn(isFieldSet);
    }

    public static <T> void mockDoCallRealFieldValidateMethod(
            FieldValidatorAction<T> validator,
            T entity, ValidatorActionType action,
            String validatedField
    ) throws ValidationException {
        mockDoCallRealValidateMethod(validator, entity, action);
        when(validator.getValidatedField()).thenReturn(validatedField);
    }
}
