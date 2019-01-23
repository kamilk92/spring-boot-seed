package pl.kkp.core.db.service.validate.action;

import org.apache.commons.lang3.StringUtils;
import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.exception.FieldNotSetException;

public abstract class FieldSetValidator<T> extends FieldValidatorAction<T> {
    public FieldSetValidator(String validatedField) {
        super(validatedField);
    }

    public void validate(T entity, ValidatorActionType targetAction) throws FieldNotSetException {
        if (!isFieldSet(entity)) {
            validatedField = getValidatedField();
            throw new FieldNotSetException(targetAction, validatedField);
        }
    }

    public abstract boolean isFieldSet(T entity);

    protected boolean isStringNotEmpty(String value) {
        return StringUtils.isNotEmpty(value);
    }
}
