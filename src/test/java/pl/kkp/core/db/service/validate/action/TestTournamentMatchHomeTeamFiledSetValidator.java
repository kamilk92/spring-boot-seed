package pl.kkp.core.db.service.validate.action;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kkp.core.db.entity.Team;
import pl.kkp.core.db.entity.TournamentMatch;
import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.exception.FieldNotSetException;
import pl.kkp.core.testing.SpringBootBaseTest;

import static org.assertj.core.api.Assertions.catchThrowable;
import static pl.kkp.core.testing.asserations.ExceptionAssertions.assertExceptionMessage;
import static pl.kkp.core.testing.mocks.FieldSetServiceValidatorMocks.buildFieldNotSetValidationMessage;

public class TestTournamentMatchHomeTeamFiledSetValidator extends SpringBootBaseTest {

    private TournamentMatch match;

    @Autowired
    private TournamentMatchHomeTeamFieldSetValidator validator;

    @Before
    public void setUp() {
        match = setUpMatch();
    }

    @Test
    public void isPassWhenMatchHomeTeamIsSet() throws FieldNotSetException {
        final int id = 0;
        Team homeTeam = new Team(id);
        match.setHomeTeam(homeTeam);
        ValidatorActionType action = ValidatorActionType.SAVE;

        validator.validate(match, action);
    }

    @Test
    public void isRaiseWhenHomeTeamNotSet() {
        Team homeTeam = new Team();
        match.setHomeTeam(homeTeam);
        ValidatorActionType action = ValidatorActionType.SAVE;

        Throwable thrown = catchThrowable(() -> validator.validate(match, action));

        String expectedMessage = buildFieldNotSetValidationMessage(
                action, TournamentMatchHomeTeamFieldSetValidator.VALIDATED_FILED);
        assertExceptionMessage(expectedMessage, FieldNotSetException.class, thrown);
    }

    private TournamentMatch setUpMatch() {
        return new TournamentMatch();
    }
}
