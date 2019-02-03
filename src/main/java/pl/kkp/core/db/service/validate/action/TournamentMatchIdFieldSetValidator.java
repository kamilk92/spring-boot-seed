package pl.kkp.core.db.service.validate.action;

import org.springframework.stereotype.Component;
import pl.kkp.core.db.entity.TournamentMatch;

@Component
public class TournamentMatchIdFieldSetValidator extends FieldSetValidator<TournamentMatch> {
    public static final String VALIDATED_FILED = "tournamentMatch.id";

    public TournamentMatchIdFieldSetValidator() {
        super(VALIDATED_FILED);
    }

    @Override
    public boolean isFieldSet(TournamentMatch entity) {
        return entity.getId() != null;
    }
}
