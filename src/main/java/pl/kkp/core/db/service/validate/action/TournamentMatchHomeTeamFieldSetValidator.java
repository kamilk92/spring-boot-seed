package pl.kkp.core.db.service.validate.action;

import org.springframework.stereotype.Component;
import pl.kkp.core.db.entity.TournamentMatch;

@Component
public class TournamentMatchHomeTeamFieldSetValidator extends FieldSetValidator<TournamentMatch> {
    public static final String VALIDATED_FILED = "homeTeam.id";

    public TournamentMatchHomeTeamFieldSetValidator() {
        super(VALIDATED_FILED);
    }

    @Override
    public boolean isFieldSet(TournamentMatch entity) {
        return ((entity.getHomeTeam() != null) && (entity.getHomeTeam().getId() != null));
    }
}
