package pl.kkp.core.db.service.validate.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kkp.core.db.entity.TournamentMatch;
import pl.kkp.core.db.entity.TournamentSeason;
import pl.kkp.core.db.repository.TournamentSeasonRepository;
import pl.kkp.core.db.service.validate.ValidatorActionType;

import java.util.Optional;

@Component
public class TournamentMatchSeasonExistValidator extends EntityExistServiceValidator<TournamentMatch>{

    public static final String VALIDATED_FIELD = "tournamentMatch.tournamentSeason";
    public static final String VALIDATED_PARAMETER = "id";

    @Autowired
    private TournamentSeasonRepository tournamentSeasonRepository;

    public TournamentMatchSeasonExistValidator() {
        super(VALIDATED_FIELD, VALIDATED_PARAMETER);
    }

    @Override
    public boolean isEntityExist(TournamentMatch entity, ValidatorActionType targetAction) {
        TournamentSeason season = entity.getTournamentSeason();
        final int seasonId = season.getId();
        Optional<TournamentSeason> foundSeason = tournamentSeasonRepository.findById(seasonId);

        return foundSeason.isPresent();
    }
}
