package pl.kkp.core.db.service.validate;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kkp.core.db.entity.Tournament;
import pl.kkp.core.db.entity.TournamentSeason;
import pl.kkp.core.db.service.validate.action.TournamentIdFieldSetInTournamentSeason;
import pl.kkp.core.db.service.validate.exception.FieldNotSetException;
import pl.kkp.core.db.service.validate.exception.ValidationException;
import pl.kkp.core.testing.SpringBootBaseTest;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static pl.kkp.core.testing.asserations.ExceptionAssertaions.assertExceptionMessage;
import static pl.kkp.core.testing.mocks.FieldSetServiceValidatorMocks.buildFiledNotSetValidationMessage;

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

        String expectedMessage = buildFiledNotSetValidationMessage(
                ValidatorActionType.SAVE, TournamentIdFieldSetInTournamentSeason.VALIDATED_TOURNAMENT_FIELD);
        assertExceptionMessage(expectedMessage, FieldNotSetException.class, thrown);
    }

    @Test
    public void isRaiseExceptionOnSaveWhenTournamentIdNotSetInTargetTournament() {
        Integer tournamentId = null;
        String name = "League 1";
        String description = "France Premier League.";
        Tournament tournament = new Tournament(tournamentId, name, description);
        tournamentSeason.setTournament(tournament);

        ValidatorActionType action = ValidatorActionType.SAVE;

        Throwable thrown = catchThrowable(() -> {
            tournamentSeasonServiceValidator.validate(tournamentSeason, action);
        });

        String expectedMessage = buildFiledNotSetValidationMessage(
                action, TournamentIdFieldSetInTournamentSeason.VALIDATED_TOURNAMENT_FIELD);
        assertExceptionMessage(expectedMessage, FieldNotSetException.class, thrown);
    }

    private TournamentSeason setupTournamentSeason() {
        Integer tournamentSeasonId = null;
        LocalDate beginDate = LocalDate.now();
        Boolean isOpen = Boolean.FALSE;
        Tournament targetTournament = null;

        return new TournamentSeason(tournamentSeasonId, beginDate, isOpen, targetTournament);
    }
}
