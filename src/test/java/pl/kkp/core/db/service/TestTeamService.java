package pl.kkp.core.db.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kkp.core.db.entity.Team;
import pl.kkp.core.db.service.validate.exception.ValidationException;
import pl.kkp.core.testing.SpringBootBaseTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TestTeamService extends SpringBootBaseTest {

    @Autowired
    private TeamService teamService;

    @Test
    public void isCreateNewTeam() throws ValidationException {
        String teamName = "Arsenal London";
        Team team = new Team(teamName);

        Team createdTeam = teamService.validateAndSave(team);

        assertThat(createdTeam).isNotNull();
        assertThat(createdTeam.getId()).isNotNull();
        assertThat(createdTeam.getName()).isEqualTo(team.getName());
    }
}
