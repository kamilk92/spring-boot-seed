package pl.kkp.core.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.kkp.core.db.entity.Tournament;
import pl.kkp.core.db.service.validate.ServiceValidator;
import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.action.TournamentNameUnique;
import pl.kkp.core.db.service.validate.action.ValidatorAction;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class TournamentServiceValidatorConfiguration {
    private Map<ValidatorActionType, List<? extends ValidatorAction<Tournament>>> actions;

    public TournamentServiceValidatorConfiguration(TournamentNameUnique tournamentNameUnique) {
        this.actions = new LinkedHashMap<ValidatorActionType, List<? extends ValidatorAction<Tournament>>>() {
            {
                put(ValidatorActionType.SAVE, Arrays.asList(tournamentNameUnique));
            }
        };
    }

    @Bean
    public TournamentServiceValidatorFactory tournamentServiceValidatorFactory() {
        return new TournamentServiceValidatorFactory(actions);
    }

    @Bean
    public ServiceValidator<Tournament> tournamentServiceValidator(
            TournamentServiceValidatorFactory tournamentServiceValidatorFactory) throws Exception {
        return tournamentServiceValidatorFactory.getObject();
    }
}
