package pl.kkp.core.bean;

import org.springframework.beans.factory.FactoryBean;
import pl.kkp.core.db.entity.User;
import pl.kkp.core.db.service.validate.ServiceValidator;
import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.action.ValidatorAction;

import java.util.List;
import java.util.Map;

public class UserServiceValidatorFactory implements FactoryBean<ServiceValidator<User>> {
    private static final Boolean IS_SINGLETON = Boolean.TRUE;

    private Map<ValidatorActionType, List<? extends ValidatorAction<User>>> actions;

    public UserServiceValidatorFactory(Map<ValidatorActionType, List<? extends ValidatorAction<User>>> actions) {
        this.actions = actions;
    }

    @Override
    public ServiceValidator<User> getObject() throws Exception {
        return new ServiceValidator<>(actions);
    }

    @Override
    public Class<?> getObjectType() {
        return ServiceValidator.class;
    }

    @Override
    public boolean isSingleton() {
        return IS_SINGLETON;
    }
}
