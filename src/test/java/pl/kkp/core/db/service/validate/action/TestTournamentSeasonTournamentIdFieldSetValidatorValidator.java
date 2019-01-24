package pl.kkp.core.db.service.validate.action;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kkp.core.db.entity.Tournament;
import pl.kkp.core.db.entity.TournamentSeason;
import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.exception.FieldNotSetException;
import pl.kkp.core.testing.SpringBootBaseTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static pl.kkp.core.testing.asserations.ExceptionAssertions.assertExceptionMessage;
import static pl.kkp.core.testing.mocks.FieldSetServiceValidatorMocks.buildFiledNotSetValidationMessage;

public class TestTournamentSeasonTournamentIdFieldSetValidatorValidator extends SpringBootBaseTest {

    private TournamentSeason tournamentSeason;

    @Autowired
    private TournamentSeasonTournamentIdFieldSetValidator tournamentSeasonTournamentIdFieldSetValidator;

    @Before
    public void setUp() {
        tournamentSeason = setUpTournamentSeason();
    }

    @Test
    public void isRaiseExceptionOnSaveWhenTournamentFieldNotSet() {
        ValidatorActionType action = ValidatorActionType.SAVE;

        Throwable thrown = catchThrowable(() -> {
            tournamentSeasonTournamentIdFieldSetValidator.validate(tournamentSeason, action);
        });

        String expectedMessage = buildFiledNotSetValidationMessage(
                action, TournamentSeasonTournamentIdFieldSetValidator.VALIDATED_FIELD);
        assertExceptionMessage(expectedMessage, FieldNotSetException.class, thrown);
    }

    public TournamentSeason setUpTournamentSeason() {
        Integer id = null;
        LocalDateTime beginDate = LocalDateTime.now();
        Boolean isOpen = Boolean.TRUE;
        Tournament targetTournament = null;

        return new TournamentSeason(id, beginDate, isOpen, targetTournament);
    }
}
