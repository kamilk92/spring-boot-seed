package pl.kkp.core.db.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kkp.core.db.entity.Tournament;
import pl.kkp.core.testing.TestJpa;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TestTournamentRepository extends TestJpa {

    @Autowired
    private TournamentRepository tournamentRepository;

    @Test
    public void isCreateNewTournament() {
        Integer id = null;
        String name = "Premier league";
        String description = "England first league.";
        Tournament tournament = new Tournament(id, name, description);

        Tournament createdTournament = tournamentRepository.save(tournament);

        assertThat(createdTournament).isNotNull();
        assertThat(createdTournament.getId()).isEqualTo(tournament.getId());
        assertThat(createdTournament.getDescription()).isEqualTo(tournament.getDescription());
    }

}
