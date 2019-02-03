package pl.kkp.core.db.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kkp.core.db.entity.TournamentMatch;
import pl.kkp.core.testing.TestJpa;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TestTournamentMatchRepository extends TestJpa {

    @Autowired
    private MatchRepository repository;

    @Test
    public void isFindAllMatchesBySeasonId() {
        final int seasonId = 0;

        List<TournamentMatch> matches = repository.findAllByTournamentSeasonId(seasonId);

        assertThat(matches).isNotEmpty()
                .extracting("id").contains(0);
    }

}
