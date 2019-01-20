package pl.kkp.core.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kkp.core.db.entity.TournamentSeason;
import pl.kkp.core.db.repository.TournamentSeasonRepository;
import pl.kkp.core.db.service.validate.ServiceValidator;
import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.exception.ValidationException;

@Service
public class TournamentSeasonService
        extends JpaRepositoryService<TournamentSeason, Integer, TournamentSeasonRepository> {
    @Autowired
    private TournamentService tournamentService;

    @Autowired
    private ServiceValidator<TournamentSeason> tournamentSeasonServiceValidator;

    @Autowired
    public TournamentSeasonService(TournamentSeasonRepository entityRepository) {
        super(entityRepository);
    }

    public TournamentSeason save(TournamentSeason season) throws ValidationException {
        tournamentSeasonServiceValidator.validate(season, ValidatorActionType.SAVE);

        return super.save(season);
    }
}
