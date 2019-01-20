package pl.kkp.core.db.service.validate.action;

import pl.kkp.core.db.entity.Tournament;
import pl.kkp.core.db.entity.TournamentSeason;
import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.exception.ValidationException;

public class TournamentIdFieldSetInTournamentSeason extends ValidatorAction<TournamentSeason> {
    public static final String TOURNAMENT_FIELD_NOT_SET = "Tournament not set in tournament season";
    public static final String TOURNAMENT_ID_FIELD_NOT_SET = "Tournament id not set in tournament.";

    @Override
    public void validate(TournamentSeason entity, ValidatorActionType targetAction) throws ValidationException {
        Tournament tournament = entity.getTournament();
        if (tournament == null) {
            throw new ValidationException(targetAction, TOURNAMENT_FIELD_NOT_SET);
        }
        if (tournament.getId() == null) {
            throw new ValidationException(targetAction, TOURNAMENT_ID_FIELD_NOT_SET);
        }
    }
}
