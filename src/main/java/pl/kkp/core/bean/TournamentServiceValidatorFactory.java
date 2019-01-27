package pl.kkp.core.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.kkp.core.db.entity.Tournament;
import pl.kkp.core.db.service.validate.ServiceValidator;
import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.action.TournamentNameFieldLengthValidator;
import pl.kkp.core.db.service.validate.action.TournamentNameFieldSetValidator;
import pl.kkp.core.db.service.validate.action.TournamentNameUniqueValidator;
import pl.kkp.core.db.service.validate.action.ValidatorAction;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class TournamentServiceValidatorFactory {
    private Map<ValidatorActionType, List<? extends ValidatorAction<Tournament>>> actions;

    public TournamentServiceValidatorFactory(
            TournamentNameFieldSetValidator tournamentNameFieldSetValidator,
            TournamentNameFieldLengthValidator tournamentNameFieldLengthValidator,
            TournamentNameUniqueValidator tournamentNameUnique) {
        this.actions = new LinkedHashMap<ValidatorActionType, List<? extends ValidatorAction<Tournament>>>() {
            {
                put(
                        ValidatorActionType.SAVE,
                        Arrays.asList(
                                tournamentNameFieldSetValidator,
                                tournamentNameFieldLengthValidator,
                                tournamentNameUnique
                        ));
            }
        };
    }

    @Bean
    public ServiceValidator<Tournament> tournamentServiceValidator() throws Exception {
        return new ServiceValidator<>(actions);
    }
}
