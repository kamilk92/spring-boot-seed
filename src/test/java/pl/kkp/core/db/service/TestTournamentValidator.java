package pl.kkp.core.db.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.kkp.core.db.entity.Tournament;
import pl.kkp.core.db.service.validate.ServiceValidator;
import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.action.TournamentNameUniqueValidator;
import pl.kkp.core.db.service.validate.exception.ValidationException;
import pl.kkp.core.testing.SpringBootBaseTest;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.Mockito.when;
import static pl.kkp.core.testing.asserations.ExceptionAssertaions.assertExceptionMessage;
import static pl.kkp.core.testing.mocks.UniqueValueServiceValidatorMocks.buildUniqueValieValidationMessage;

public class TestTournamentValidator extends SpringBootBaseTest {

    @MockBean
    private TournamentService mockTournamentService;

    @Autowired
    private ServiceValidator<Tournament> tournamentServiceValidator;

    private Tournament tournament;

    @Before
    public void setUp() {
        tournament = setUpTournament();
    }

    @Test
    public void isNotRaiseExceptionWhenTournamentNameIsUnique() throws ValidationException {
        ValidatorActionType action = ValidatorActionType.SAVE;

        when(mockTournamentService.findByName(tournament.getName())).thenReturn(null);

        tournamentServiceValidator.validate(tournament, action);
    }

    @Test
    public void isRaiseExceptionWhenTournamentNameIsNotUnique() {
        ValidatorActionType action = ValidatorActionType.SAVE;

        when(mockTournamentService.findByName(tournament.getName())).thenReturn(tournament);

        Throwable thrown = catchThrowable(() -> {
            tournamentServiceValidator.validate(tournament, action);
        });

        String expectedMessage = buildUniqueValieValidationMessage(
                action, TournamentNameUniqueValidator.VALIDATED_FIELD);
        assertExceptionMessage(expectedMessage, ValidationException.class, thrown);
    }

    private Tournament setUpTournament() {
        String name = "Test tournament";
        String description = "This is test tournament";
        return new Tournament(name, description);
    }

}
