package pl.kkp.core.bean;

import org.springframework.beans.factory.FactoryBean;
import pl.kkp.core.db.entity.Tournament;
import pl.kkp.core.db.service.validate.ServiceValidator;
import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.action.ValidatorAction;

import java.util.List;
import java.util.Map;

public class TournamentServiceValidatorFactory implements FactoryBean<ServiceValidator<Tournament>> {
    private static final Boolean IS_SINGLETON = Boolean.TRUE;

    private Map<ValidatorActionType, List<? extends ValidatorAction<Tournament>>> actions;

    public TournamentServiceValidatorFactory(Map<ValidatorActionType,
            List<? extends ValidatorAction<Tournament>>> actions) {
        this.actions = actions;
    }

    @Override
    public ServiceValidator<Tournament> getObject() throws Exception {
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
