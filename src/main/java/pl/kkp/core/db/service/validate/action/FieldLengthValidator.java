package pl.kkp.core.db.service.validate.action;

import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.exception.FieldLengthTooLongException;
import pl.kkp.core.db.service.validate.exception.FieldLengthTooShortException;
import pl.kkp.core.db.service.validate.exception.ValidationException;

public abstract class FieldLengthValidator<T> extends FieldValidatorAction<T> {
    private Integer minFieldLengthInclusive;
    private Integer maxFieldLengthInclusive;

    public FieldLengthValidator(String validatedField, int minFieldLengthInclusive, int maxFieldLengthInclusive) {
        super(validatedField);
        this.minFieldLengthInclusive = minFieldLengthInclusive;
        this.maxFieldLengthInclusive = maxFieldLengthInclusive;
    }

    @Override
    public void validate(T entity, ValidatorActionType targetAction) throws ValidationException {
        int fieldLength = getFieldLength(entity);
        int minFieldLength = getMinFieldLengthInclusive();
        if (fieldLength < minFieldLength) {
            String validatedField = getValidatedField();
            throw new FieldLengthTooShortException(targetAction, validatedField, fieldLength, minFieldLength);
        }
        int maxFieldLength = getMaxFieldLengthInclusive();
        if (fieldLength > maxFieldLength) {
            String validatedField = getValidatedField();
            throw new FieldLengthTooLongException(targetAction, validatedField, fieldLength, maxFieldLength);
        }
    }

    public Integer getMinFieldLengthInclusive() {
        return minFieldLengthInclusive;
    }

    public Integer getMaxFieldLengthInclusive() {
        return maxFieldLengthInclusive;
    }

    public abstract int getFieldLength(T entity);
}
