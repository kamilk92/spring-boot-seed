package pl.kkp.core.db.service.validate;

import pl.kkp.core.db.service.validate.action.ValidatorAction;
import pl.kkp.core.db.service.validate.exception.ValidationException;

import java.util.List;
import java.util.Map;

public class ServiceValidator<T> {
    protected Map<ValidatorActionType, List<? extends ValidatorAction<T>>> actions;

    public ServiceValidator(Map<ValidatorActionType, List<? extends ValidatorAction<T>>> actions) {
        this.actions = actions;
    }

    public void validate(T entity, ValidatorActionType action) throws ValidationException {
        List<? extends ValidatorAction<T>> validators = actions.get(action);

        if ((validators == null)) {
            return;
        }

        for (ValidatorAction<T> validator : validators) {
            validator.validate(entity, action);
        }
    }
}
