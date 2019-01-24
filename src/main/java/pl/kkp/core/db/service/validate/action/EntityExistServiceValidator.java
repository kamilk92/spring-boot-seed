package pl.kkp.core.db.service.validate.action;

import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.exception.EntityNotExistException;
import pl.kkp.core.db.service.validate.exception.ValidationException;

public abstract class EntityExistServiceValidator<T> extends FieldValidatorAction<T> {
    private String validatedParameter;

    public EntityExistServiceValidator(String validatedField, String validatedParameter) {
        super(validatedField);
        this.validatedParameter = validatedParameter;
    }

    @Override
    public void validate(T entity, ValidatorActionType targetAction) throws ValidationException {
        if (!isEntityExist(entity, targetAction)) {
            String validatedField = getValidatedField();
            String validatedParameter = getValidatedParameter();
            throw new EntityNotExistException(targetAction, validatedField, validatedParameter);
        }
    }

    public abstract boolean isEntityExist(T entity, ValidatorActionType targetAction);

    public String getValidatedParameter() {
        return validatedParameter;
    }

    public void setValidatedParameter(String validatedParameter) {
        this.validatedParameter = validatedParameter;
    }
}
