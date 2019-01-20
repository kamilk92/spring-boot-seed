package pl.kkp.core.bean;

import org.springframework.beans.factory.FactoryBean;
import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.action.TournamentIdFieldSetInTournamentSeason;

import java.util.List;

public class TournamentIdFieldSetInTournamentSeasonFactory
        implements FactoryBean<TournamentIdFieldSetInTournamentSeason> {
    private static final Boolean IS_SINGLETON = true;

    private List<ValidatorActionType> actionTypes;

    @Override
    public TournamentIdFieldSetInTournamentSeason getObject() throws Exception {
        return new TournamentIdFieldSetInTournamentSeason();
    }

    @Override
    public Class<?> getObjectType() {
        return TournamentIdFieldSetInTournamentSeason.class;
    }

    @Override
    public boolean isSingleton() {
        return IS_SINGLETON;
    }
}
