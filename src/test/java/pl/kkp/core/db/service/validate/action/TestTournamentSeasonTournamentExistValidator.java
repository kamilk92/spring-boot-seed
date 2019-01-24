package pl.kkp.core.db.service.validate.action;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.kkp.core.db.entity.Tournament;
import pl.kkp.core.db.entity.TournamentSeason;
import pl.kkp.core.db.repository.TournamentRepository;
import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.exception.EntityNotExistException;
import pl.kkp.core.db.service.validate.exception.ValidationException;
import pl.kkp.core.testing.SpringBootBaseTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.Mockito.when;
import static pl.kkp.core.testing.asserations.ExceptionAssertions.assertExceptionMessage;
import static pl.kkp.core.testing.mocks.EntityExistServiceValidatorMocks.buildEntityExistValidationMessage;

public class TestTournamentSeasonTournamentExistValidator extends SpringBootBaseTest {
    private static final int TOURNAMENT_ID = 1;

    @Autowired
    private TournamentSeasonTournamentExistValidator tournamentExistValidator;

    @MockBean
    private TournamentRepository repository;

    private TournamentSeason season;

    @Before
    public void setUp() {
        season = setUpSeason();
    }

    @Test
    public void isPassWhenTournamentExist() throws ValidationException {
        Optional<Tournament> foundTournament = Optional.of(season.getTournament());
        when(repository.findById(TOURNAMENT_ID)).thenReturn(foundTournament);
        ValidatorActionType action = ValidatorActionType.SAVE;

        tournamentExistValidator.validate(season, action);
    }

    @Test
    public void isRaiseExceptionWhenTournamentNotExist() {
        Optional<Tournament> foundTournament = Optional.empty();
        when(repository.findById(TOURNAMENT_ID)).thenReturn(foundTournament);
        ValidatorActionType action = ValidatorActionType.SAVE;

        Throwable thrown = catchThrowable(() -> {
            tournamentExistValidator.validate(season, action);
        });

        String expectedMsg = buildEntityExistValidationMessage(tournamentExistValidator, action);
        assertExceptionMessage(expectedMsg, EntityNotExistException.class, thrown);
    }

    private TournamentSeason setUpSeason() {
        Tournament tournament = new Tournament(TOURNAMENT_ID);

        return new TournamentSeason(tournament);
    }
}
