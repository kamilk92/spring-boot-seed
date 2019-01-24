package pl.kkp.core.testing.mocks;

import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.action.EntityExistServiceValidator;
import pl.kkp.core.db.service.validate.exception.EntityNotExistException;
import pl.kkp.core.db.service.validate.exception.ValidationException;

import static org.mockito.Mockito.when;
import static pl.kkp.core.testing.mocks.FieldSetServiceValidatorMocks.mockDoCallRealFieldValidateMethod;
import static pl.kkp.core.testing.mocks.ServiceValidatorMocks.buildValidationMessage;
import static pl.kkp.core.testing.mocks.ServiceValidatorMocks.mockDoCallRealValidateMethod;

public class EntityExistServiceValidatorMocks {
    public static String buildEntityExistValidationMessage(
            EntityExistServiceValidator entityExistValidator, ValidatorActionType action) {
        String validatedField = entityExistValidator.getValidatedField();
        String validatedParameter = entityExistValidator.getValidatedParameter();

        return buildEntityExistValidationMessage(action, validatedField, validatedParameter);
    }

    public static String buildEntityExistValidationMessage(
            ValidatorActionType action, String validatedField, String validatedParameter) {
        String failureReason = String.format(
                EntityNotExistException.FAILURE_REASON_FMT, validatedField, validatedParameter);

        return buildValidationMessage(action, failureReason);
    }

    public static <T> void mockDoCallIsEntityExistValidateMethod(
            EntityExistServiceValidator<T> validator,
            T entity,
            ValidatorActionType action,
            String validatedField,
            String validatedParameter,
            boolean isEntityExist
    ) throws ValidationException {
        mockDoCallRealFieldValidateMethod(validator, entity, action, validatedField);
        when(validator.getValidatedParameter()).thenReturn(validatedParameter);
        when(validator.isEntityExist(entity, action)).thenReturn(isEntityExist);
    }
}
