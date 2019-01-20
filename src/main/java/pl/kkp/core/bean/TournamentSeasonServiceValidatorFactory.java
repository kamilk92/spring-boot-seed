package pl.kkp.core.bean;

import org.springframework.beans.factory.FactoryBean;
import pl.kkp.core.db.entity.Tournament;
import pl.kkp.core.db.entity.TournamentSeason;
import pl.kkp.core.db.service.validate.ServiceValidator;
import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.action.ValidatorAction;

import java.util.List;
import java.util.Map;

public class TournamentSeasonServiceValidatorFactory implements FactoryBean<ServiceValidator<TournamentSeason>> {
    private static final Boolean IS_SINGLETON = true;

    private Map<ValidatorActionType, List<? extends ValidatorAction<TournamentSeason>>> actions;

    public TournamentSeasonServiceValidatorFactory(
            Map<ValidatorActionType, List<? extends ValidatorAction<TournamentSeason>>> actions) {
        this.actions = actions;
    }

    @Override
    public ServiceValidator<TournamentSeason> getObject() throws Exception {
        return new ServiceValidator<TournamentSeason>(actions);
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
