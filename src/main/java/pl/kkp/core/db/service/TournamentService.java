package pl.kkp.core.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kkp.core.db.entity.Tournament;
import pl.kkp.core.db.repository.TournamentRepository;
import pl.kkp.core.db.service.validate.ServiceValidator;
import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.exception.ValidationException;

@Service
public class TournamentService extends JpaRepositoryService<Tournament, Integer, TournamentRepository> {
    @Autowired
    private ServiceValidator<Tournament> tournamentServiceValidator;

    @Autowired
    public TournamentService(TournamentRepository tournamentRepository) {
        super(tournamentRepository);
    }

    public Tournament findByName(String name) {
        return entityRepository.findByName(name);
    }

    public Tournament save(Tournament tournament) throws ValidationException {
        tournamentServiceValidator.validate(tournament, ValidatorActionType.SAVE);

        return super.save(tournament);
    }
}
