package pl.kkp.core.db.service.validate.action;

import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.exception.ValidationException;

public abstract class ValidatorAction<T> {
    public abstract void validate(T entity, ValidatorActionType targetAction) throws ValidationException;
}
