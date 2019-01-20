package pl.kkp.core.db.service.validate.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import pl.kkp.core.db.entity.Tournament;
import pl.kkp.core.db.service.TournamentService;
import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.exception.ValidationException;

@Component
public class TournamentNameUnique extends ValidatorAction<Tournament> {
    public static final String TOURNAMENT_NAME_ALREADY_EXIST = "Tournament with name '%s' already exist.";

    @Autowired
    @Lazy
    private TournamentService tournamentService;

    @Override
    public void validate(Tournament entity, ValidatorActionType targetAction) throws ValidationException {
        String tournamentName = entity.getName();
        Tournament foundTournament = tournamentService.findByName(tournamentName);
        if (foundTournament != null) {
            String failureReason = String.format(TOURNAMENT_NAME_ALREADY_EXIST, entity.getName());
            throw new ValidationException(targetAction, failureReason);
        }
    }
}
