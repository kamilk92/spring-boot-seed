package pl.kkp.core.db.service.validate.action;

import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.exception.ValidationException;

public interface ValidatorAction<T> {
    void validate(T entity, ValidatorActionType targetAction) throws ValidationException;
}
