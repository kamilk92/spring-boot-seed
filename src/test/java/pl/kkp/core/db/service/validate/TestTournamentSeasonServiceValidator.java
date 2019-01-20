package pl.kkp.core.db.service.validate;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kkp.core.db.entity.Tournament;
import pl.kkp.core.db.entity.TournamentSeason;
import pl.kkp.core.db.service.validate.ServiceValidator;
import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.action.TournamentIdFieldSetInTournamentSeason;
import pl.kkp.core.db.service.validate.exception.ValidationException;
import pl.kkp.core.testing.SpringBootBaseTest;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

public class TestTournamentSeasonServiceValidator extends SpringBootBaseTest {

    @Autowired
    private ServiceValidator<TournamentSeason> tournamentSeasonServiceValidator;

    private TournamentSeason tournamentSeason;

    @Before
    public void setUp() {
        tournamentSeason = setupTournamentSeason();
    }

    @Test
    public void isRaiseExceptionOnSaveWhenTournamentNotSetInTournamentSeason() throws ValidationException {
        ValidatorActionType actionType = ValidatorActionType.SAVE;

        Throwable thrown = catchThrowable(() -> {
            tournamentSeasonServiceValidator.validate(tournamentSeason, actionType);
        });

        String failureReason = TournamentIdFieldSetInTournamentSeason.TOURNAMENT_FIELD_NOT_SET;
        String expectedMessage = String.format(ValidationException.EXCEPTION_MESSAGE, actionType, failureReason);

        assertValidationExceptionMessage(thrown, expectedMessage);
    }

    @Test
    public void isRaiseExceptionOnSaveWhenTournamentIdNotSetInTargetTournament() {
        Integer tournamentId = null;
        String name = "League 1";
        String description = "France Premier League.";
        Tournament tournament = new Tournament(tournamentId, name, description);
        tournamentSeason.setTournament(tournament);

        ValidatorActionType actionType = ValidatorActionType.SAVE;

        Throwable thrown = catchThrowable(() -> {
            tournamentSeasonServiceValidator.validate(tournamentSeason, actionType);
        });

        String failureReason = TournamentIdFieldSetInTournamentSeason.TOURNAMENT_ID_FIELD_NOT_SET;
        String expectedMessage = String.format(ValidationException.EXCEPTION_MESSAGE, actionType, failureReason);

        assertValidationExceptionMessage(thrown, expectedMessage);
    }

    private void assertValidationExceptionMessage(Throwable thrown, String expectedMessage) {
        assertThat(thrown)
                .isInstanceOf(ValidationException.class)
                .hasMessage(expectedMessage);
    }

    private TournamentSeason setupTournamentSeason() {
        Integer tournamentSeasonId = null;
        LocalDate beginDate = LocalDate.now();
        Boolean isOpen = Boolean.FALSE;
        Tournament targetTournament = null;

        return new TournamentSeason(tournamentSeasonId, beginDate, isOpen, targetTournament);
    }
}
