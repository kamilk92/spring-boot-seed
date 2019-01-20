package pl.kkp.core.db.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kkp.core.db.entity.Tournament;
import pl.kkp.core.db.entity.TournamentSeason;
import pl.kkp.core.testing.TestJpa;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TestTournamentSeasonRepository extends TestJpa {
    @Autowired
    private TournamentSeasonRepository tournamentSeasonRepository;

    @Autowired
    private TournamentRepository tournamentRepository;

    @Test
    public void isCreateNewTournamentSeason() {
        Integer id = null;
        LocalDate beginDate = LocalDate.now();
        Boolean isOpen = Boolean.TRUE;
        Tournament tournament = null;

        TournamentSeason createdSeason = saveTournamentSeason(id, beginDate, isOpen, tournament);

        assertThat(createdSeason).isNotNull();
        assertThat(createdSeason.getId()).isNotNull();
        assertThat(createdSeason.getBeginDate()).isEqualTo(beginDate);
        assertThat(createdSeason.getOpen()).isEqualTo(isOpen);
        assertThat(createdSeason.getTournament()).isEqualTo(tournament);
    }

    @Test
    public void isCreateNewSeasonAssignToTournament() {
        Integer tournamentId = null;
        String tournamentName = "Premier league";
        String tournamentDescription = "England first league.";
        Tournament tournament = saveTournament(tournamentId, tournamentName, tournamentDescription);
        assertThat(tournament.getId()).isNotNull();

        Integer seasonId = null;
        LocalDate beginDate = LocalDate.now();
        Boolean isOpen = Boolean.TRUE;

        TournamentSeason createdSeason = saveTournamentSeason(seasonId, beginDate, isOpen, tournament);

        assertThat(createdSeason).isNotNull();
        assertThat(createdSeason.getId()).isNotNull();
        assertThat(createdSeason.getTournament()).isNotNull();
        assertThat(createdSeason.getTournament().getId()).isEqualTo(tournament.getId());
    }

    private Tournament saveTournament(Integer id, String name, String description) {
        Tournament tournament = new Tournament(id, name, description);
        tournament = tournamentRepository.save(tournament);

        return tournament;
    }

    private TournamentSeason saveTournamentSeason(
            Integer id, LocalDate beginDate, Boolean isOpen, Tournament tournament) {
        TournamentSeason season = new TournamentSeason(id, beginDate, isOpen, tournament);
        TournamentSeason createdSeason = tournamentSeasonRepository.save(season);

        return createdSeason;
    }
}
