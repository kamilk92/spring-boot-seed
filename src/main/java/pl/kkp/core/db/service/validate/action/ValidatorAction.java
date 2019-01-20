package pl.kkp.core.db.service.validate.action;

import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.exception.ValidationException;

import java.util.List;

public abstract class ValidatorAction<T> {
    protected List<ValidatorActionType> validatorActionTypes;

    public abstract void validate(T entity, ValidatorActionType targetAction) throws ValidationException;
}
