package pl.kkp.core.db.service.validate.action;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kkp.core.db.entity.TournamentSeason;
import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.exception.FieldNotSetException;
import pl.kkp.core.testing.SpringBootBaseTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static pl.kkp.core.testing.asserations.ExceptionAssertions.assertExceptionMessage;
import static pl.kkp.core.testing.mocks.FieldSetServiceValidatorMocks.buildFieldNotSetValidationMessage;

public class TestTournamentSeasonBeginDateSet extends SpringBootBaseTest {

    private TournamentSeason season;

    @Autowired
    private TournamentSeasonBeginDateFieldSetValidator tournamentSeasonBeginDateFieldSetValidator;

    public TestTournamentSeasonBeginDateSet() {
        season = setUpSeason();
    }

    @Test
    public void isPassWhenSeasonBeginDateIsSet() throws FieldNotSetException {
        ValidatorActionType action = ValidatorActionType.SAVE;
        tournamentSeasonBeginDateFieldSetValidator.validate(season, action);
    }

    @Test
    public void isRaiseExceptionWhenSeasonBeginDateIsNotSet() {
        season.setBeginDate(null);

        ValidatorActionType action = ValidatorActionType.SAVE;
        Throwable thrown = catchThrowable(() -> {
            tournamentSeasonBeginDateFieldSetValidator.validate(season, action);
        });

        String validatedFieldName = TournamentSeasonBeginDateFieldSetValidator.VALIDATED_FIELD;
        String expectedMsg = buildFieldNotSetValidationMessage(action, validatedFieldName);
        assertExceptionMessage(expectedMsg, FieldNotSetException.class, thrown);
    }

    private TournamentSeason setUpSeason() {
        LocalDateTime beginDate = LocalDateTime.now();
        return new TournamentSeason(beginDate);
    }
}
