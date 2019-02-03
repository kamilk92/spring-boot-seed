package pl.kkp.core.controller.model;

import java.time.LocalDateTime;

public class TournamentMatchModel {
    private Integer id;
    private LocalDateTime beginDate;
    private Integer homeScore;
    private Integer awayScore;
    private TeamModel homeTeam;
    private TeamModel awayTeam;
    private TournamentSeasonModel tournamentSeason;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDateTime beginDate) {
        this.beginDate = beginDate;
    }

    public Integer getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(Integer homeScore) {
        this.homeScore = homeScore;
    }

    public Integer getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(Integer awayScore) {
        this.awayScore = awayScore;
    }

    public TeamModel getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(TeamModel homeTeam) {
        this.homeTeam = homeTeam;
    }

    public TeamModel getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(TeamModel awayTeam) {
        this.awayTeam = awayTeam;
    }

    public TournamentSeasonModel getTournamentSeason() {
        return tournamentSeason;
    }

    public void setTournamentSeason(TournamentSeasonModel tournamentSeason) {
        this.tournamentSeason = tournamentSeason;
    }
}
