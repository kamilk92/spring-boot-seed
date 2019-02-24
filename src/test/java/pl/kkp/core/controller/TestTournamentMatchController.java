package pl.kkp.core.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import pl.kkp.core.db.entity.Team;
import pl.kkp.core.db.entity.TournamentMatch;
import pl.kkp.core.db.entity.TournamentSeason;
import pl.kkp.core.security.basic.http.BasicCredentials;
import pl.kkp.core.testing.TestRestController;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.kkp.core.testing.asserations.RestResponseAssertions.assertResponseStatusCodeOk;

public class TestTournamentMatchController extends TestRestController {

    private TournamentMatch match;

    @Autowired
    private BasicCredentials adminCredentials;

    public TestTournamentMatchController() {
        super("");
    }

    @Before
    public void setUp() {
        match = setUpMatch();
    }

    @Test
    public void isCreateNewTournamentMatch() {
        final int seasonId = 1;
        String endpointPath = String.format("/season/%d/match", seasonId);
        endpointPath = getEndpointPath(endpointPath);

        ResponseEntity<TournamentMatch> response = authorizedPost(
                adminCredentials, endpointPath, match, TournamentMatch.class);

        assertResponseStatusCodeOk(response);

        TournamentMatch createdMatch = response.getBody();
        assertThat(createdMatch).isNotNull();
        assertThat(createdMatch.getId()).isNotNull();
    }

    @Test
    public void isListAllSeasonMatches() {
        final int seasonId = 0;
        String endpointPath = String.format("season/%d/matches", seasonId);
        endpointPath = getEndpointPath(endpointPath);

        ParameterizedTypeReference<List<TournamentMatch>> rspType =
                new ParameterizedTypeReference<List<TournamentMatch>>(){};
        ResponseEntity<List<TournamentMatch>> response = authorizedGet(
                adminCredentials, endpointPath, rspType);

        assertResponseStatusCodeOk(response);
        List<TournamentMatch> matches = response.getBody();
        assertThat(matches)
                .extracting("id")
                .contains(0);
    }

    @Test
    public void isListAllMatches() {
        String endpointPath = getEndpointPath("/matches");

        ParameterizedTypeReference<List<TournamentMatch>> rspType =
                new ParameterizedTypeReference<List<TournamentMatch>>()g{};
        ResponseEntity<List<TournamentMatch>> response = authorizedGet(
                adminCredentials, endpointPath, rspType);

        assertResponseStatusCodeOk(response);
        List<TournamentMatch> matches = response.getBody();
        assertThat(matches)
                .extracting("id")
                .contains(0);
    }

    @Test
    public void isUpdateMatchResult() {
        final int seasonId = 1;
        String endpointPath = String.format("/season/%d/match", seasonId);
        endpointPath = getEndpointPath(endpointPath);

        ResponseEntity<TournamentMatch> response = authorizedPost(
                adminCredentials, endpointPath, match, TournamentMatch.class);

        assertResponseStatusCodeOk(response);

        TournamentMatch matchToUpdate = response.getBody();
        assertThat(matchToUpdate).isNotNull();
        assertThat(matchToUpdate.getId()).isNotNull();

        endpointPath = String.format("/match/%d", matchToUpdate.getId());
        endpointPath = getEndpointPath(endpointPath);
        final int homeScore = matchToUpdate.getHomeScore() + 1;
        final int awayScore = matchToUpdate.getAwayScore() + 2;
        matchToUpdate.setHomeScore(homeScore);
        matchToUpdate.setAwayScore(awayScore);

        response = authorizedPut(adminCredentials, endpointPath, matchToUpdate, TournamentMatch.class);

        assertResponseStatusCodeOk(response);

        TournamentMatch updatedMatch = response.getBody();
        assertThat(updatedMatch).isNotNull();
        assertThat(updatedMatch.getId()).isEqualTo(matchToUpdate.getId());
        assertThat(updatedMatch.getHomeScore()).isEqualTo(homeScore);
        assertThat(updatedMatch.getAwayScore()).isEqualTo(awayScore);
    }

    private TournamentMatch setUpMatch() {
        final int homeTeamId = 1;
        Team homeTeam = new Team(homeTeamId);
        final int awayTeamId = 0;
        Team awayTeam = new Team(awayTeamId);
        final int homeScore = 3;
        final int awayScore = 2;
        final int seasonId = 0;
        TournamentSeason season = new TournamentSeason(seasonId);
        LocalDateTime beginDate = LocalDateTime.now();

        return new TournamentMatch(beginDate, homeScore, awayScore, homeTeam, awayTeam, season);
    }
}
