package pl.kkp.core.testing.mocks;

import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.action.ValidatorAction;
import pl.kkp.core.db.service.validate.exception.ValidationException;

import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;

public class ServiceValidatorMocks {
    public static String buildValidationMessage(ValidatorActionType action, String failureReason) {
        return String.format(ValidationException.EXCEPTION_MESSAGE, action, failureReason);
    }

    public static <T> void mockDoCallRealValidateMethod(
            ValidatorAction<T> validator, T entity, ValidatorActionType action) throws ValidationException {
        doCallRealMethod().when(validator).validate(entity, action);
    }

    public static <T> void mockDoNothingOnValidateMethod(
            ValidatorAction<T> validator, T entity, ValidatorActionType action) throws ValidationException {
        doNothing().when(validator).validate(entity, action);
    }
}
