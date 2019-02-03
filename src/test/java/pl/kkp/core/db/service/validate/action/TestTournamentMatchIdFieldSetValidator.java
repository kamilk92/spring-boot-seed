package pl.kkp.core.db.service.validate.action;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kkp.core.db.entity.TournamentMatch;
import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.exception.FieldNotSetException;
import pl.kkp.core.testing.SpringBootBaseTest;

import static org.assertj.core.api.Assertions.catchThrowable;
import static pl.kkp.core.testing.asserations.ExceptionAssertions.assertExceptionMessage;
import static pl.kkp.core.testing.mocks.FieldSetServiceValidatorMocks.buildFieldNotSetValidationMessage;

public class TestTournamentMatchIdFieldSetValidator extends SpringBootBaseTest {

    private TournamentMatch match;

    @Autowired
    private TournamentMatchIdFieldSetValidator matchIdFieldSetValidator;

    @Before
    public void setUp() {
        match = setUpMatch();
    }

    @Test
    public void isPassWhenTournamentMatchIdSet() throws FieldNotSetException {
        matchIdFieldSetValidator.validate(match, ValidatorActionType.UPDATE);
    }

    @Test
    public void isRaiseWhenTournamentMatchIdIsNotSet() {
        match.setId(null);
        ValidatorActionType action = ValidatorActionType.UPDATE;

        Throwable thrown = catchThrowable(() -> matchIdFieldSetValidator.validate(match, action));

        String validatedField = TournamentMatchIdFieldSetValidator.VALIDATED_FILED;
        String expectedMsg = buildFieldNotSetValidationMessage(action, validatedField);
        assertExceptionMessage(expectedMsg, FieldNotSetException.class, thrown);
    }

    private TournamentMatch setUpMatch() {
        final int matchId = 0;
        return new TournamentMatch(matchId);
    }
}
