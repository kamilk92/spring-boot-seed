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

public class TestTournamentMatchAwayTemFieldSetValidator extends SpringBootBaseTest {

    private TournamentMatch match;

    @Autowired
    private TournamentMatchAwayTeamFieldSetValidator validator;

    @Before
    public void setUp() {
        match = setUpMatch();
    }

    @Test
    public void isPassWhenMatchAwayTeamIsSet() throws FieldNotSetException {
        final int id = 0;
        Team awayTeam = new Team(id);
        match.setAwayTeam(awayTeam);
        ValidatorActionType action = ValidatorActionType.SAVE;

        validator.validate(match, action);
    }

    @Test
    public void isRaiseWhenAwayTeamNotSet(){
        Team awayTeam = new Team();
        match.setAwayTeam(awayTeam);
        ValidatorActionType action = ValidatorActionType.SAVE;

        Throwable thrown = catchThrowable(() -> validator.validate(match, action));

        String expectedMsg = buildFieldNotSetValidationMessage(
                action, TournamentMatchAwayTeamFieldSetValidator.VALIDATED_FIELD);
        assertExceptionMessage(expectedMsg, FieldNotSetException.class, thrown);
    }

    private TournamentMatch setUpMatch() {
        return new TournamentMatch();
    }
}
