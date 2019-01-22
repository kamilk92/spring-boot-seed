package pl.kkp.core.db.service.validate.action;

public abstract class FieldValidatorAction<T> implements ValidatorAction<T> {
    protected String validatedField;

    public FieldValidatorAction(String validatedField) {
        this.validatedField = validatedField;
    }

    public String getValidatedField() {
        return validatedField;
    }
}
