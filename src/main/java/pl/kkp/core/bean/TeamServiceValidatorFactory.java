package pl.kkp.core.bean;

import org.junit.Before;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.kkp.core.db.entity.Team;
import pl.kkp.core.db.service.validate.ServiceValidator;
import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.action.TeamNameFieldSetValidator;
import pl.kkp.core.db.service.validate.action.TeamNameLengthValidator;
import pl.kkp.core.db.service.validate.action.TeamNameUniqueValidator;
import pl.kkp.core.db.service.validate.action.ValidatorAction;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class TeamServiceValidatorFactory {

    private Map<ValidatorActionType, List<? extends ValidatorAction<Team>>> actions;

    public TeamServiceValidatorFactory(
            TeamNameFieldSetValidator teamNameFieldSetValidator,
            TeamNameLengthValidator teamNameLengthValidator,
            TeamNameUniqueValidator teamNameUniqueValidator
    ) {
        this.actions = new LinkedHashMap<ValidatorActionType, List<? extends ValidatorAction<Team>>>() {
            {
                put(
                        ValidatorActionType.SAVE,
                        Arrays.asList(
                                teamNameFieldSetValidator,
                                teamNameLengthValidator,
                                teamNameUniqueValidator
                        )
                );
            }
        };
    }

    @Bean
    public ServiceValidator<Team> teamServiceValidator() {
        return new ServiceValidator<>(actions);
    }
}
