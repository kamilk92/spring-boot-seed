package pl.kkp.core.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kkp.core.db.entity.Tournament;
import pl.kkp.core.db.repository.TournamentRepository;

@Service
public class TournamentService extends JpaRepositoryService<Tournament, Integer, TournamentRepository> {
    @Autowired
    public TournamentService(TournamentRepository tournamentRepository) {
        super(tournamentRepository);
    }
}
