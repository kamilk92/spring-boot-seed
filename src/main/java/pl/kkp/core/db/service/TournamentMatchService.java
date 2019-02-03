package pl.kkp.core.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kkp.core.db.entity.TournamentMatch;
import pl.kkp.core.db.entity.TournamentSeason;
import pl.kkp.core.db.repository.MatchRepository;
import pl.kkp.core.db.service.validate.ServiceValidator;
import pl.kkp.core.db.service.validate.exception.ValidationException;

import java.util.List;

@Service
public class TournamentMatchService extends JpaRepositoryService<TournamentMatch, Integer, MatchRepository> {

    @Autowired
    private ServiceValidator<TournamentMatch> tournamentMatchServiceValidator;

    @Autowired
    public TournamentMatchService(
            MatchRepository matchRepository, ServiceValidator<TournamentMatch> matchServiceValidator) {
        super(matchRepository, matchServiceValidator);
    }

    public List<TournamentMatch> findByTournamentSeasonId(Integer tournamentSeasonId) {
        return entityRepository.findAllByTournamentSeasonId(tournamentSeasonId);
    }

    public TournamentMatch save(TournamentMatch tournamentMatch, Integer seasonId) throws ValidationException {
        TournamentSeason season = new TournamentSeason(seasonId);
        tournamentMatch.setTournamentSeason(season);

        return validateAndSave(tournamentMatch);
    }

    public TournamentMatch save(TournamentMatch tournamentMatch) throws ValidationException {
        return validateAndSave(tournamentMatch);
    }
}
