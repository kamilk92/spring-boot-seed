package pl.kkp.core.db.service.validate.action;

import org.springframework.stereotype.Component;
import pl.kkp.core.db.entity.TournamentSeason;

@Component
public class TournamentSeasonTournamentIdFieldSetValidator extends FieldSetValidator<TournamentSeason> {
    public static final String VALIDATED_FIELD = "tournament.id";

    public TournamentSeasonTournamentIdFieldSetValidator() {
        super(VALIDATED_FIELD);
    }

    @Override
    public boolean isFieldSet(TournamentSeason entity) {
        return (isTournamentSet(entity) && (isTournamentIdSet(entity)));
    }

    private boolean isTournamentIdSet(TournamentSeason season) {
        return season.getTournament().getId() != null;
    }

    private boolean isTournamentSet(TournamentSeason season) {
        return season.getTournament() != null;
    }
}
