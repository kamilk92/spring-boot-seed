package pl.kkp.core.db.repository;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kkp.core.db.entity.Team;
import pl.kkp.core.db.entity.TournamentMatch;
import pl.kkp.core.db.entity.TournamentSeason;
import pl.kkp.core.testing.TestJpa;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TestTournamentMatchRepository extends TestJpa {

    private TournamentMatch match;

    @Autowired
    private MatchRepository repository;

    @Before
    public void setUp() {
        match = setUpMatch();
    }

    @Test
    public void isFindAllMatchesBySeasonId() {
        final int seasonId = 0;

        List<TournamentMatch> matches = repository.findAllByTournamentSeasonId(seasonId);

        assertThat(matches).isNotEmpty()
                .extracting("id").contains(0);
    }

    @Test
    public void isUpdateMatchResult() {
        TournamentMatch savedMatch = repository.save(match);
        assertThat(savedMatch).isNotNull();
        assertThat(savedMatch.getId()).isNotNull();

        final int matchId = savedMatch.getId();
        final int homeScore = savedMatch.getHomeScore() + 2;
        final int awayScore = savedMatch.getAwayScore() + 1;

        repository.updateMatchResult(matchId, homeScore, awayScore);

        TournamentMatch updatedMatch = repository.findById(savedMatch.getId()).get();
        assertThat(updatedMatch).isNotNull();
        assertThat(updatedMatch.getHomeScore()).isEqualTo(homeScore);
        assertThat(updatedMatch.getAwayScore()).isEqualTo(awayScore);
    }

    private TournamentMatch setUpMatch() {
        LocalDateTime beginDate = LocalDateTime.now();
        final int homeScore = 0;
        final int awayScore = 0;
        final int homeTeamId = 0;
        Team homeTeam = new Team(homeTeamId);
        final int awayTeamId = 1;
        Team awayTeam = new Team(awayTeamId);
        final int tournamentSeasonId = 0;
        TournamentSeason season = new TournamentSeason(tournamentSeasonId);

        return new TournamentMatch(beginDate, homeScore, awayScore, homeTeam, awayTeam, season);
    }
}
