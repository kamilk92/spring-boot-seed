package pl.kkp.core.db.service.validate.action;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.kkp.core.db.entity.Tournament;
import pl.kkp.core.db.service.TournamentService;
import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.exception.ValidationException;
import pl.kkp.core.testing.SpringBootBaseTest;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.Mockito.when;
import static pl.kkp.core.testing.asserations.ExceptionAssertaions.assertExceptionMessage;
import static pl.kkp.core.testing.mocks.UniqueValueServiceValidatorMocks.buildUniqueValieValidationMessage;


public class TestTournamentNameUnique extends SpringBootBaseTest {

    private Tournament tournament;

    @Autowired
    private TournamentNameUniqueValidator tournamentNameUnique;

    @MockBean
    private TournamentService tournamentService;

    @Before
    public void setUp() {
        tournament = setUpTournament();
    }

    @Test
    public void isNotRaiseExceptionWhenTournamentNameUnique() throws ValidationException {
        ValidatorActionType action = ValidatorActionType.SAVE;
        when(tournamentService.findByName(tournament.getName())).thenReturn(null);

        tournamentNameUnique.validate(tournament, action);
    }

    @Test
    public void isRaiseExceptionWhenTournamentNameIsNotUnique() {
        ValidatorActionType action = ValidatorActionType.SAVE;
        when(tournamentService.findByName(tournament.getName())).thenReturn(tournament);

        Throwable thrown = catchThrowable(() -> {
            tournamentNameUnique.validate(tournament, action);
        });

        String expectedMessage = buildUniqueValieValidationMessage(
                action, TournamentNameUniqueValidator.VALIDATED_FIELD);
        assertExceptionMessage(expectedMessage, ValidationException.class, thrown);
    }

    private Tournament setUpTournament() {
        String name = "Premier Division";
        String description = "Spain first league.";

        return new Tournament(name, description);
    }
}
