package pl.kkp.core.db.service.validate.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import pl.kkp.core.db.entity.Tournament;
import pl.kkp.core.db.service.TournamentService;

@Component
public class TournamentNameUniqueValidator extends UniqueValueValidator<Tournament> {
    public static final String VALIDATED_FIELD = "tournament.name";

    @Autowired
    @Lazy
    private TournamentService tournamentService;

    public TournamentNameUniqueValidator() {
        super(VALIDATED_FIELD);
    }

    @Override
    protected boolean isUniqueValue(Tournament entity) {
        String tournamentName = entity.getName();
        Tournament foundTournament = tournamentService.findByName(tournamentName);

        return foundTournament == null;
    }
}
