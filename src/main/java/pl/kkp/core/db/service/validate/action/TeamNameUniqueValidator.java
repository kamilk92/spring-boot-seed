package pl.kkp.core.db.service.validate.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kkp.core.db.entity.Team;
import pl.kkp.core.db.repository.TeamRepository;

@Component
public class TeamNameUniqueValidator extends UniqueValueValidator<Team> {

    public static final String VALIDATED_FIELD = "team.name";

    @Autowired
    private TeamRepository teamRepository;

    public TeamNameUniqueValidator() {
        super(VALIDATED_FIELD);
    }

    @Override
    public boolean isUniqueValue(Team entity) {
        String teamName = entity.getName();
        Team team = teamRepository.findByName(teamName);

        return team == null;
    }
}
