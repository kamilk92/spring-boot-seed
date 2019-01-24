package pl.kkp.core.db.service.validate.action;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kkp.core.db.entity.Tournament;
import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.exception.FieldLengthTooLongException;
import pl.kkp.core.db.service.validate.exception.ValidationException;
import pl.kkp.core.testing.SpringBootBaseTest;
import pl.kkp.core.testing.asserations.ExceptionAssertaions;
import pl.kkp.core.testing.mocks.FieldLengthServiceValidatorMocks;
import pl.kkp.core.util.RandomStringGenerator;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static pl.kkp.core.testing.asserations.ExceptionAssertaions.assertExceptionMessage;
import static pl.kkp.core.testing.mocks.FieldLengthServiceValidatorMocks.buildFieldTooLongValidationMessage;

public class TestTournamentFieldLengthValidator extends SpringBootBaseTest {

    private RandomStringGenerator tournamentNameGenerator;

    private Tournament tournament;

    @Autowired
    private TournamentNameFieldLengthValidator tournamentNameFieldLengthValidator;

    public TestTournamentFieldLengthValidator() {
        this.tournamentNameGenerator =
                new RandomStringGenerator(TournamentNameFieldLengthValidator.MAX_NAME_LENGTH + 1);
    }

    @Before
    public void setUp() {
        tournament = setUpTournament();
    }

    @Test
    public void isPassWhenTournamentNameLengthIsValid() throws ValidationException {
        ValidatorActionType action = ValidatorActionType.SAVE;
        tournamentNameFieldLengthValidator.validate(tournament, action);
    }

    @Test
    public void isRaiseExceptionWhenTournamentNameLengthIsTooLong() {
        String tournamentName = tournamentNameGenerator.generate();
        tournament.setName(tournamentName);
        ValidatorActionType action = ValidatorActionType.SAVE;

        Throwable thrown = catchThrowable(() -> {
            tournamentNameFieldLengthValidator.validate(tournament, action);
        });
        int actualLen = tournamentName.length();
        String expectedMessage = buildFieldTooLongValidationMessage(
                action, tournamentNameFieldLengthValidator, actualLen);
        assertExceptionMessage(expectedMessage, FieldLengthTooLongException.class, thrown);
    }

    private Tournament setUpTournament() {
        String tournamentName = "League One.";
        String tournamentDescription = "First France League.";
        Tournament tournament = new Tournament(tournamentName, tournamentDescription);

        return tournament;
    }
}
