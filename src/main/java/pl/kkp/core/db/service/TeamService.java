package pl.kkp.core.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kkp.core.db.entity.Team;
import pl.kkp.core.db.repository.TeamRepository;
import pl.kkp.core.db.service.validate.ServiceValidator;
import pl.kkp.core.db.service.validate.exception.ValidationException;

@Service
public class TeamService extends JpaRepositoryService<Team, Integer, TeamRepository> {

    @Autowired
    public TeamService(TeamRepository teamRepository, ServiceValidator<Team> teamServiceValidator) {
        super(teamRepository, teamServiceValidator);
    }

    @Override
    public Team save(Team team) throws ValidationException {
        return validateAndSave(team);
    }
}
