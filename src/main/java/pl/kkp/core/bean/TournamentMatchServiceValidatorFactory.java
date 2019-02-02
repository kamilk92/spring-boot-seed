package pl.kkp.core.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.kkp.core.db.entity.TournamentMatch;
import pl.kkp.core.db.service.validate.ServiceValidator;
import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.action.TournamentMatchAwayTeamExistValidator;
import pl.kkp.core.db.service.validate.action.TournamentMatchAwayTeamFieldSetValidator;
import pl.kkp.core.db.service.validate.action.TournamentMatchHomeTeamExistValidator;
import pl.kkp.core.db.service.validate.action.TournamentMatchHomeTeamFieldSetValidator;
import pl.kkp.core.db.service.validate.action.TournamentMatchSeasonExistValidator;
import pl.kkp.core.db.service.validate.action.TournamentMatchSeasonIdFieldSetValidator;
import pl.kkp.core.db.service.validate.action.ValidatorAction;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class TournamentMatchServiceValidatorFactory {

    private Map<ValidatorActionType, List<? extends ValidatorAction<TournamentMatch>>> actions;

    @Autowired
    public TournamentMatchServiceValidatorFactory(
            TournamentMatchHomeTeamFieldSetValidator homeTeamFieldSetValidator,
            TournamentMatchHomeTeamExistValidator homeTeamExistValidator,
            TournamentMatchAwayTeamFieldSetValidator awayTeamFieldSetValidator,
            TournamentMatchAwayTeamExistValidator awayTeamExistValidator,
            TournamentMatchSeasonIdFieldSetValidator matchSeasonIdFieldSetValidator,
            TournamentMatchSeasonExistValidator matchSeasonExistValidator
    ) {
        this.actions = new LinkedHashMap<ValidatorActionType, List<? extends ValidatorAction<TournamentMatch>>>() {
            {
                put(
                        ValidatorActionType.SAVE,
                        Arrays.asList(
                                homeTeamFieldSetValidator,
                                homeTeamExistValidator,
                                awayTeamFieldSetValidator,
                                awayTeamExistValidator,
                                matchSeasonIdFieldSetValidator,
                                matchSeasonExistValidator
                        )
                );
            }
        };
    }

    @Bean
    public ServiceValidator<TournamentMatch> tournamentMatchServiceValidator() {
        return new ServiceValidator<>(actions);
    }
}
