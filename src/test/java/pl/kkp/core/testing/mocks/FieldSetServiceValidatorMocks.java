package pl.kkp.core.testing.mocks;

import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.action.FieldValidatorAction;
import pl.kkp.core.db.service.validate.exception.FieldNotSetException;
import pl.kkp.core.db.service.validate.exception.ValidationException;

import static org.mockito.Mockito.when;

public class FieldSetServiceValidatorMocks {
    public static String buildFiledNotSetValidationMessage(ValidatorActionType action, String fieldName) {
        String failureReason = String.format(FieldNotSetException.FAILURE_REASON_FMT, fieldName);

        return ServiceValidatorMocks.buildValidationMessage(action, failureReason);
    }

    public static <T> void mockDoCallRealFieldValidateMethod(
            FieldValidatorAction<T> validator, T entity, ValidatorActionType action, String validatedField)
            throws ValidationException {

        ServiceValidatorMocks.mockDoCallRealValidateMethod(validator, entity, action);
        when(validator.getValidatedField()).thenReturn(validatedField);
    }
}
