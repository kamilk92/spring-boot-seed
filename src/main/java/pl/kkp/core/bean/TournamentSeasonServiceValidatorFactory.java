package pl.kkp.core.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.kkp.core.db.entity.TournamentSeason;
import pl.kkp.core.db.service.validate.ServiceValidator;
import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.action.TournamentSeasonBeginDateFieldSetValidator;
import pl.kkp.core.db.service.validate.action.TournamentSeasonTournamentExistValidator;
import pl.kkp.core.db.service.validate.action.TournamentSeasonTournamentIdFieldSetValidator;
import pl.kkp.core.db.service.validate.action.ValidatorAction;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class TournamentSeasonServiceValidatorFactory {
    private Map<ValidatorActionType, List<? extends ValidatorAction<TournamentSeason>>> actions;

    @Autowired
    public TournamentSeasonServiceValidatorFactory(
            TournamentSeasonTournamentIdFieldSetValidator tournamentSeasonTournamentIdFieldSetValidator,
            TournamentSeasonBeginDateFieldSetValidator seasonBeginDateFieldSetValidator,
            TournamentSeasonTournamentExistValidator tournamentSeasonTournamentExistValidator
    ) {
        this.actions = new LinkedHashMap<ValidatorActionType, List<? extends ValidatorAction<TournamentSeason>>>() {
            {
                put(
                        ValidatorActionType.SAVE,
                        Arrays.asList(
                                tournamentSeasonTournamentIdFieldSetValidator,
                                seasonBeginDateFieldSetValidator,
                                tournamentSeasonTournamentExistValidator
                        )
                );
            }
        }
        ;
    }

    @Bean
    public ServiceValidator<TournamentSeason> tournamentSeasonServiceValidator() throws Exception {
        return new ServiceValidator<>(actions);
    }
}
