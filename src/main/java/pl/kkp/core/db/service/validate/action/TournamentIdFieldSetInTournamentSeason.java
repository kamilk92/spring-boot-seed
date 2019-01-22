package pl.kkp.core.db.service.validate.action;

import org.springframework.stereotype.Component;
import pl.kkp.core.db.entity.TournamentSeason;

@Component
public class TournamentIdFieldSetInTournamentSeason extends FieldSetValidator<TournamentSeason> {
    public static final String VALIDATED_TOURNAMENT_FIELD = "tournament.id";

    public TournamentIdFieldSetInTournamentSeason() {
        super(VALIDATED_TOURNAMENT_FIELD);
    }

    @Override
    protected boolean isFieldSet(TournamentSeason entity) {
        return (isTournamentSet(entity) && (isTournamentIdSet(entity)));
    }

    private boolean isTournamentIdSet(TournamentSeason season) {
        return season.getTournament().getId() != null;
    }

    private boolean isTournamentSet(TournamentSeason season) {
        return season.getTournament() != null;
    }
}
