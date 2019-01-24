package pl.kkp.core.db.service.validate.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kkp.core.db.entity.Tournament;
import pl.kkp.core.db.entity.TournamentSeason;
import pl.kkp.core.db.repository.TournamentRepository;
import pl.kkp.core.db.service.validate.ValidatorActionType;

import java.util.Optional;

@Component
public class TournamentSeasonTournamentExistValidator extends EntityExistServiceValidator<TournamentSeason> {
    public static final String VALIDATED_FIELD = "tournamentSeason.tournament";
    public static final String VALIDATED_PARAMETER = "id";

    @Autowired
    private TournamentRepository repository;

    public TournamentSeasonTournamentExistValidator() {
        super(VALIDATED_FIELD, VALIDATED_PARAMETER);
    }

    @Override
    public boolean isEntityExist(TournamentSeason entity, ValidatorActionType targetAction) {
        Tournament tournament = entity.getTournament();
        Integer tournamentId = tournament.getId();
        Optional<Tournament> foundTournament = repository.findById(tournamentId);

        return foundTournament.isPresent();
    }
}
