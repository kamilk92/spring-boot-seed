package pl.kkp.core.db.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class TournamentSeason {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime beginDate;
    private Boolean isOpen;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tournament_id", nullable = false)
    private Tournament tournament;

    public TournamentSeason() {
    }

    public TournamentSeason(LocalDateTime beginDate) {
        this.beginDate = beginDate;
    }

    public TournamentSeason(Tournament tournament) {
        this.tournament = tournament;
    }

    public TournamentSeason(LocalDateTime beginDate, Boolean isOpen, Tournament tournament) {
        this.beginDate = beginDate;
        this.isOpen = isOpen;
        this.tournament = tournament;
    }

    public TournamentSeason(Integer id, LocalDateTime beginDate, Boolean isOpen, Tournament tournament) {
        this.id = id;
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

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }
}
