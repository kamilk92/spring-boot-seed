package pl.kkp.core.db.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kkp.core.db.entity.Team;
import pl.kkp.core.testing.SpringBootBaseTest;
import pl.kkp.core.testing.TestJpa;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TestTeamRepository extends TestJpa {

    private static final String EXISTING_TEAM_NAME = "Test team";

    @Autowired
    private TeamRepository teamRepository;

    @Test
    public void isFindTeamByName() {
        Team team = teamRepository.findByName(EXISTING_TEAM_NAME);

        assertThat(team).isNotNull();
        assertThat(team.getName()).isEqualTo(EXISTING_TEAM_NAME);
    }

}
