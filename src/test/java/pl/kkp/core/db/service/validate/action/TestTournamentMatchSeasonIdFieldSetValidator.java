package pl.kkp.core.db.service.validate.action;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kkp.core.db.entity.TournamentMatch;
import pl.kkp.core.db.entity.TournamentSeason;
import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.exception.FieldNotSetException;
import pl.kkp.core.testing.SpringBootBaseTest;

import static org.assertj.core.api.Assertions.catchThrowable;
import static pl.kkp.core.testing.asserations.ExceptionAssertions.assertExceptionMessage;
import static pl.kkp.core.testing.mocks.FieldSetServiceValidatorMocks.buildFieldNotSetValidationMessage;

public class TestTournamentMatchSeasonIdFieldSetValidator extends SpringBootBaseTest {

    private TournamentMatch match;

    @Autowired
    private TournamentMatchSeasonIdFieldSetValidator matchSeasonIdFieldSetValidator;

    @Before
    public void setUp() {
        match = setUpMatch();
    }

    @Test
    public void isPassWhenTournamentMatchSeasonIdSet() throws FieldNotSetException {
        ValidatorActionType action = ValidatorActionType.SAVE;
        final int seasonId = 0;
        TournamentSeason season = new TournamentSeason(seasonId);
        match.setTournamentSeason(season);

        matchSeasonIdFieldSetValidator.validate(match, action);
    }

    @Test
    public void isRaiseExceptionWhenTournamentMatchSeasonIdNotSet() throws FieldNotSetException {
        ValidatorActionType action = ValidatorActionType.SAVE;
        TournamentSeason season = new TournamentSeason();
        match.setTournamentSeason(season);

        Throwable thrown = catchThrowable(() -> matchSeasonIdFieldSetValidator.validate(match, action));

        String expectedMessage = buildFieldNotSetValidationMessage(action,
                TournamentMatchSeasonIdFieldSetValidator.VALIDATED_FILED);
        assertExceptionMessage(expectedMessage, FieldNotSetException.class, thrown);
    }

    private TournamentMatch setUpMatch() {
        return new TournamentMatch();
    }
}
