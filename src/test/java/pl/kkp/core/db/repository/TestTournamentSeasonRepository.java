package pl.kkp.core.db.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kkp.core.db.entity.Tournament;
import pl.kkp.core.db.entity.TournamentSeason;
import pl.kkp.core.testing.TestJpa;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


public class TestTournamentSeasonRepository extends TestJpa {
    @Autowired
    private TournamentSeasonRepository tournamentSeasonRepository;

    @Autowired
    private TournamentRepository tournamentRepository;

    @Test
    public void isCreateNewTournamentSeason() {
        Integer id = null;
        LocalDateTime beginDate = LocalDateTime.now();
        Boolean isOpen = Boolean.TRUE;
        Integer tournamentId = 0;
        Tournament tournament = new Tournament(tournamentId);

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
        LocalDateTime beginDate = LocalDateTime.now();
        Boolean isOpen = Boolean.TRUE;

        TournamentSeason createdSeason = saveTournamentSeason(seasonId, beginDate, isOpen, tournament);

        assertThat(createdSeason).isNotNull();
        assertThat(createdSeason.getId()).isNotNull();
        assertThat(createdSeason.getTournament()).isNotNull();
        assertThat(createdSeason.getTournament().getId()).isEqualTo(tournament.getId());
    }

    @Test
    public void isReturnTournamentSeasonsByTournamentId() {
        Integer tournamentId = 0;

        List<TournamentSeason> tournamentSeasons = tournamentSeasonRepository.findByTournamentId(tournamentId);

        assertThat(tournamentSeasons.size()).isGreaterThanOrEqualTo(2);
        assertThat(tournamentSeasons)
                .extracting("id")
                .contains(0, 1);
    }

    private Tournament saveTournament(Integer id, String name, String description) {
        Tournament tournament = new Tournament(id, name, description);
        tournament = tournamentRepository.save(tournament);

        return tournament;
    }

    private TournamentSeason saveTournamentSeason(
            Integer id, LocalDateTime beginDate, Boolean isOpen, Tournament tournament) {
        TournamentSeason season = new TournamentSeason(id, beginDate, isOpen, tournament);
        TournamentSeason createdSeason = tournamentSeasonRepository.save(season);

        return createdSeason;
    }
}
