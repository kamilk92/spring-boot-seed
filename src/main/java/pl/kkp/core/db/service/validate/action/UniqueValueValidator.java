package pl.kkp.core.db.service.validate.action;

import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.exception.NotUniqueValueException;
import pl.kkp.core.db.service.validate.exception.ValidationException;

public abstract class UniqueValueValidator<T> implements ValidatorAction<T> {
    private String validatedField;

    public UniqueValueValidator(String validatedField) {
        this.validatedField = validatedField;
    }

    @Override
    public void validate(T entity, ValidatorActionType targetAction) throws ValidationException {
        if (!isUniqueValue(entity)) {
            throw new NotUniqueValueException(targetAction, validatedField);
        }
    }

    protected abstract boolean isUniqueValue(T entity);
}
