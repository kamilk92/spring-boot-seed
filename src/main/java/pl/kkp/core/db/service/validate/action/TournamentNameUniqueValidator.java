package pl.kkp.core.db.service.validate.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kkp.core.db.entity.Tournament;
import pl.kkp.core.db.repository.TournamentRepository;

@Component
public class TournamentNameUniqueValidator extends UniqueValueValidator<Tournament> {
    public static final String VALIDATED_FIELD = "tournament.name";

    private TournamentRepository tournamentRepository;

    @Autowired
    public TournamentNameUniqueValidator(TournamentRepository tournamentRepository) {
        super(VALIDATED_FIELD);
        this.tournamentRepository = tournamentRepository;
    }

    @Override
    public boolean isUniqueValue(Tournament entity) {
        String tournamentName = entity.getName();
        Tournament foundTournament = tournamentRepository.findByName(tournamentName);

        return foundTournament == null;
    }
}
