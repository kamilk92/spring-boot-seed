package pl.kkp.core.db.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.kkp.core.db.entity.Team;
import pl.kkp.core.db.entity.TournamentMatch;
import pl.kkp.core.db.entity.TournamentSeason;
import pl.kkp.core.db.service.exception.EntityWithIdNotFoundException;
import pl.kkp.core.db.service.validate.ServiceValidator;
import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.exception.ValidationException;
import pl.kkp.core.testing.SpringBootBaseTest;

import javax.transaction.NotSupportedException;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;

public class TestTournamentMatchService extends SpringBootBaseTest {

    private static final int EXISTING_MATCH_ID = 0;
    private static final int EXISTING_MATCH_0_AWAY_SCORE = 2;
    private static final String EXISING_MATCH_0_AWAY_TEAM = "Test team 2";
    private static final int EXISTING_MATCH_0_HOME_SCORE = 1;
    private static final String EXISITING_MATCH_0_HOME_TEAM = "Test team";

    private TournamentMatch match;

    @Autowired
    private TournamentMatchService tournamentMatchService;

    @MockBean
    private ServiceValidator<TournamentMatch> tournamentMatchServiceValidator;

    @Before
    public void setUp() {
        match = setUpMatch();
    }

    @Test
    public void isFindTournamentMatchById() throws EntityWithIdNotFoundException {
        TournamentMatch match = tournamentMatchService.findById(EXISTING_MATCH_ID);

        assertThat(match).isNotNull();
        assertThat(match.getId()).isEqualTo(EXISTING_MATCH_ID);
        assertThat(match.getHomeScore()).isEqualTo(EXISTING_MATCH_0_HOME_SCORE);
        assertThat(match.getAwayScore()).isEqualTo(EXISTING_MATCH_0_AWAY_SCORE);
        assertThat(match.getHomeTeam()).isNotNull();
        assertThat(match.getHomeTeam().getName()).isEqualTo(EXISITING_MATCH_0_HOME_TEAM);
        assertThat(match.getAwayTeam()).isNotNull();
        assertThat(match.getAwayTeam().getName()).isEqualTo(EXISING_MATCH_0_AWAY_TEAM);
        assertThat(match.getTournamentSeason()).isNotNull();
        assertThat(match.getTournamentSeason().getId()).isEqualTo(0);
    }

    @Test
    public void isSaveNewTournamentMatch() throws ValidationException {
        doNothing().when(tournamentMatchServiceValidator).validate(match, ValidatorActionType.SAVE);

        TournamentMatch savedMatch = tournamentMatchService.save(match);

        assertThat(savedMatch).isNotNull();
        assertThat(savedMatch.getId()).isNotNull();
    }

    @Test
    public void isUpdateMatchResult() throws ValidationException, NotSupportedException {
        doNothing().when(tournamentMatchServiceValidator).validate(match, ValidatorActionType.UPDATE);

        TournamentMatch savedMatch = tournamentMatchService.save(match);
        assertThat(savedMatch).isNotNull();
        assertThat(savedMatch.getId()).isNotNull();

        final int matchId = savedMatch.getId();
        final int homeScore = savedMatch.getHomeScore() + 3;
        final int awayScore = savedMatch.getAwayScore() + 1;
        TournamentMatch updatedMatch = new TournamentMatch(matchId, homeScore, awayScore);

        updatedMatch = tournamentMatchService.updateMatchResult(updatedMatch);

        assertThat(updatedMatch).isNotNull();
        assertThat(updatedMatch.getId()).isEqualTo(matchId);
        assertThat(updatedMatch.getHomeScore()).isEqualTo(homeScore);
        assertThat(updatedMatch.getAwayScore()).isEqualTo(awayScore);
    }

    private TournamentMatch setUpMatch() {
        final int homeScore = 2;
        final int awayScore = 1;
        final int homeTeamId = 1;
        Team homeTeam = new Team(homeTeamId);
        final int awayTeamId = 0;
        Team awayTeam = new Team(awayTeamId);
        final int seasonId = 0;
        TournamentSeason season = new TournamentSeason(seasonId);
        LocalDateTime beginDate = LocalDateTime.now();

        return new TournamentMatch(beginDate, homeScore, awayScore, homeTeam, awayTeam, season);
    }
}
