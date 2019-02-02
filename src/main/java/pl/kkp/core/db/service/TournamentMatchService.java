package pl.kkp.core.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kkp.core.db.entity.TournamentMatch;
import pl.kkp.core.db.repository.MatchRepository;
import pl.kkp.core.db.service.validate.ServiceValidator;

@Service
public class TournamentMatchService extends JpaRepositoryService<TournamentMatch, Integer, MatchRepository> {

    @Autowired
    public TournamentMatchService(MatchRepository matchRepository, ServiceValidator<TournamentMatch> matchServiceValidator) {
        super(matchRepository, matchServiceValidator);
    }
}
