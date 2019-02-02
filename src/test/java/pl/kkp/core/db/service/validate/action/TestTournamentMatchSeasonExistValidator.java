package pl.kkp.core.db.service.validate.action;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.kkp.core.db.entity.TournamentMatch;
import pl.kkp.core.db.entity.TournamentSeason;
import pl.kkp.core.db.repository.TournamentSeasonRepository;
import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.exception.EntityNotExistException;
import pl.kkp.core.db.service.validate.exception.ValidationException;
import pl.kkp.core.testing.SpringBootBaseTest;

import javax.persistence.EntityExistsException;
import javax.transaction.NotSupportedException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.when;
import static pl.kkp.core.testing.asserations.ExceptionAssertions.assertExceptionMessage;
import static pl.kkp.core.testing.mocks.EntityExistServiceValidatorMocks.buildEntityExistValidationMessage;

public class TestTournamentMatchSeasonExistValidator extends SpringBootBaseTest {

    private TournamentMatch match;

    @Autowired
    private TournamentMatchSeasonExistValidator matchSeasonExistValidator;

    @MockBean
    private TournamentSeasonRepository tournamentSeasonRepository;

    @Before
    public void setUp(){
        match = setUpMatch();
    }

    @Test
    public void isPassWhenTournamentMatchSeasonExist() throws ValidationException {
        final int tournamentSeasonId = 0;
        TournamentSeason season = new TournamentSeason(tournamentSeasonId);
        when(tournamentSeasonRepository.findById(tournamentSeasonId)).thenReturn(Optional.of(season));
        match.setTournamentSeason(season);
        ValidatorActionType action = ValidatorActionType.SAVE;

        matchSeasonExistValidator.validate(match, action);
    }

    @Test
    public void isPassWhenTournamentMatchSeasonNotExist() throws NotSupportedException {
        final int tournamentSeasonId = 0;
        TournamentSeason season = new TournamentSeason(tournamentSeasonId);
        when(tournamentSeasonRepository.findById(tournamentSeasonId)).thenReturn(Optional.empty());
        match.setTournamentSeason(season);
        ValidatorActionType action = ValidatorActionType.SAVE;

        Throwable thrown = catchThrowable(() -> matchSeasonExistValidator.validate(match, action));

        String expectedMsg = buildEntityExistValidationMessage(matchSeasonExistValidator, action);
        assertExceptionMessage(expectedMsg, EntityNotExistException.class, thrown);
    }

    private TournamentMatch setUpMatch(){
        return new TournamentMatch();
    }

}
