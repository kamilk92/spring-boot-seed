package pl.kkp.core.db.service.validate.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kkp.core.db.entity.Team;
import pl.kkp.core.db.entity.TournamentMatch;
import pl.kkp.core.db.repository.TeamRepository;
import pl.kkp.core.db.service.validate.ValidatorActionType;

import java.util.Optional;

@Component
public class TournamentMatchHomeTeamExistValidator extends EntityExistServiceValidator<TournamentMatch> {
    public static final String VALIDATED_FIELD = "tournamentMatch.homeTeam";
    public static final String VALIDATED_PARAMETER = "id";

    @Autowired
    private TeamRepository teamRepository;

    public TournamentMatchHomeTeamExistValidator() {
        super(VALIDATED_FIELD, VALIDATED_PARAMETER);
    }

    @Override
    public boolean isEntityExist(TournamentMatch entity, ValidatorActionType targetAction) {
        Team homeTeam = entity.getHomeTeam();
        Integer homeTeamId = homeTeam.getId();
        Optional<Team> foundTeam = teamRepository.findById(homeTeamId);

        return foundTeam.isPresent();
    }
}
