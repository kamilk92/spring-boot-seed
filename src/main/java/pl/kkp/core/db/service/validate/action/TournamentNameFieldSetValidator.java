package pl.kkp.core.db.service.validate.action;

import org.springframework.context.annotation.Configuration;
import pl.kkp.core.db.entity.Tournament;

@Configuration
public class TournamentNameFieldSetValidator extends FieldSetValidator<Tournament> {
    public static final String VALIDATED_FIELD = "tournament.name";

    public TournamentNameFieldSetValidator() {
        super(VALIDATED_FIELD);
    }

    @Override
    public boolean isFieldSet(Tournament entity) {
        return isStringNotEmpty(entity.getName());
    }
}
