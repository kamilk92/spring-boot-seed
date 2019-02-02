package pl.kkp.core.db.service.validate.action;

import org.springframework.stereotype.Component;
import pl.kkp.core.db.entity.TournamentMatch;

@Component
public class TournamentMatchAwayTeamFieldSetValidator extends FieldSetValidator<TournamentMatch> {

    public static final String VALIDATED_FIELD = "tournamentMatch.awayTeam.id";

    public TournamentMatchAwayTeamFieldSetValidator() {
        super(VALIDATED_FIELD);
    }

    @Override
    public boolean isFieldSet(TournamentMatch entity) {
        return ((entity.getAwayTeam() != null) && (entity.getAwayTeam().getId() != null));
    }
}
