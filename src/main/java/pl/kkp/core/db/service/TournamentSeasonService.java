package pl.kkp.core.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kkp.core.db.entity.Tournament;
import pl.kkp.core.db.entity.TournamentSeason;
import pl.kkp.core.db.repository.TournamentSeasonRepository;
import pl.kkp.core.db.service.validate.ServiceValidator;
import pl.kkp.core.db.service.validate.exception.ValidationException;

import java.util.List;

@Service
public class TournamentSeasonService
        extends JpaRepositoryService<TournamentSeason, Integer, TournamentSeasonRepository> {

    @Autowired
    public TournamentSeasonService(
            TournamentSeasonRepository entityRepository,
            ServiceValidator<TournamentSeason> tournamentSeasonServiceValidator) {
        super(entityRepository, tournamentSeasonServiceValidator);
    }

    public List<TournamentSeason> findByTournamentId(Integer tournamentId) {
        return entityRepository.findByTournamentId(tournamentId);
    }

    public TournamentSeason save(TournamentSeason season) throws ValidationException {
        return validateAndSave(season);
    }

    public TournamentSeason save(TournamentSeason season, Integer tournamentId) throws ValidationException {
        Tournament tournament = new Tournament(tournamentId);
        season.setTournament(tournament);

        return validateAndSave(season);
    }
}
