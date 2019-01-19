package pl.kkp.core.db.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class TournamentSeason {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private LocalDate beginDate;
    private Boolean isOpen;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tournament_id", nullable = false)
    private Tournament tournament;

    public TournamentSeason() {
    }

    public TournamentSeason(Integer id, LocalDate beginDate, Boolean isOpen, Tournament tournament) {
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

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDate beginDate) {
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
