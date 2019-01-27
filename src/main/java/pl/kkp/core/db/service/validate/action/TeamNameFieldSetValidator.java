package pl.kkp.core.db.service.validate.action;

import org.springframework.stereotype.Component;
import pl.kkp.core.db.entity.Team;

@Component
public class TeamNameFieldSetValidator extends FieldSetValidator<Team> {

    public static final String VALIDATED_FIELD = "team.name";

    public TeamNameFieldSetValidator() {
        super(VALIDATED_FIELD);
    }

    @Override
    public boolean isFieldSet(Team entity) {
        return isStringNotEmpty(entity.getName());
    }
}
