package pl.kkp.core.controller.model;

import java.time.LocalDateTime;
import java.util.Date;

public class TournamentSeasonModel {
    private Integer id;
    private LocalDateTime beginDate;
    private Boolean isOpen;
    private TournamentModel tournament;

    public TournamentSeasonModel() {
    }

    public TournamentSeasonModel(LocalDateTime beginDate) {
        this.beginDate = beginDate;
    }

    public TournamentSeasonModel(LocalDateTime beginDate, Boolean isOpen) {
        this.beginDate = beginDate;
        this.isOpen = isOpen;
    }

    public TournamentSeasonModel(LocalDateTime beginDate, boolean isOpen, TournamentModel tournament) {
        this.beginDate = beginDate;
        this.isOpen = isOpen;
        this.tournament = tournament;
    }

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

    public Boolean getOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }

    public TournamentModel getTournament() {
        return tournament;
    }

    public void setTournament(TournamentModel tournament) {
        this.tournament = tournament;
    }
}
