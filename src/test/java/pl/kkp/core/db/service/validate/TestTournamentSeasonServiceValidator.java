package pl.kkp.core.db.service.validate;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.kkp.core.db.entity.Tournament;
import pl.kkp.core.db.entity.TournamentSeason;
import pl.kkp.core.db.service.validate.action.TournamentSeasonBeginDateFieldSetValidator;
import pl.kkp.core.db.service.validate.action.TournamentSeasonTournamentExistValidator;
import pl.kkp.core.db.service.validate.action.TournamentSeasonTournamentIdFieldSetValidator;
import pl.kkp.core.db.service.validate.exception.EntityNotExistException;
import pl.kkp.core.db.service.validate.exception.FieldNotSetException;
import pl.kkp.core.db.service.validate.exception.ValidationException;
import pl.kkp.core.testing.SpringBootBaseTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static pl.kkp.core.testing.asserations.ExceptionAssertions.assertExceptionMessage;
import static pl.kkp.core.testing.mocks.EntityExistServiceValidatorMocks.buildEntityExistValidationMessage;
import static pl.kkp.core.testing.mocks.EntityExistServiceValidatorMocks.mockDoCallIsEntityExistValidateMethod;
import static pl.kkp.core.testing.mocks.FieldSetServiceValidatorMocks.buildFieldNotSetValidationMessage;
import static pl.kkp.core.testing.mocks.FieldSetServiceValidatorMocks.mockDoCallIsFieldSetValidateMethod;
import static pl.kkp.core.testing.mocks.ServiceValidatorMocks.mockDoNothingOnValidateMethod;

public class TestTournamentSeasonServiceValidator extends SpringBootBaseTest {

    @Autowired
    private ServiceValidator<TournamentSeason> tournamentSeasonServiceValidator;

    @MockBean
    private TournamentSeasonBeginDateFieldSetValidator seasonBeginDateFieldSetValidator;

    @MockBean
    private TournamentSeasonTournamentExistValidator tournamentExistValidator;

    @MockBean
    private TournamentSeasonTournamentIdFieldSetValidator tournamentIdFieldSetValidator;

    private TournamentSeason season;

    @Before
    public void setUp() {
        season = setupTournamentSeason();
    }

    @Test
    public void isRaiseExceptionOnSaveWhenTournamentNotSetInTournamentSeason() throws ValidationException {
        ValidatorActionType action = ValidatorActionType.SAVE;
        String validatedField = TournamentSeasonTournamentIdFieldSetValidator.VALIDATED_FIELD;
        final boolean isTournamentIdFieldSet = false;
        mockDoCallIsFieldSetValidateMethod(
                tournamentIdFieldSetValidator, season, action, validatedField, isTournamentIdFieldSet);
        mockDoNothingOnValidateMethod(seasonBeginDateFieldSetValidator, season, action);
        mockDoNothingOnValidateMethod(tournamentExistValidator, season, action);

        Throwable thrown = catchThrowable(() -> {
            tournamentSeasonServiceValidator.validate(season, action);
        });

        String expectedMessage = buildFieldNotSetValidationMessage(
                ValidatorActionType.SAVE, TournamentSeasonTournamentIdFieldSetValidator.VALIDATED_FIELD);
        assertExceptionMessage(expectedMessage, FieldNotSetException.class, thrown);
    }

    @Test
    public void isRaiseExceptionOnSaveWhenTournamentIdNotSetInTargetTournament() throws ValidationException {
        Integer tournamentId = null;
        String name = "League 1";
        String description = "France Premier League.";
        Tournament tournament = new Tournament(tournamentId, name, description);
        season.setTournament(tournament);

        ValidatorActionType action = ValidatorActionType.SAVE;
        String validatedField = TournamentSeasonTournamentIdFieldSetValidator.VALIDATED_FIELD;
        final boolean isTournamentIdFieldSet = false;
        mockDoCallIsFieldSetValidateMethod(
                tournamentIdFieldSetValidator, season, action, validatedField, isTournamentIdFieldSet);
        mockDoNothingOnValidateMethod(seasonBeginDateFieldSetValidator, season, action);
        mockDoNothingOnValidateMethod(tournamentExistValidator, season, action);

        Throwable thrown = catchThrowable(() -> {
            tournamentSeasonServiceValidator.validate(season, action);
        });

        String expectedMessage = buildFieldNotSetValidationMessage(
                action, TournamentSeasonTournamentIdFieldSetValidator.VALIDATED_FIELD);
        assertExceptionMessage(expectedMessage, FieldNotSetException.class, thrown);
    }

    @Test
    public void isRaiseExceptionWhenBeginDateFieldIsNotSet() throws ValidationException {
        season.setBeginDate(null);
        ValidatorActionType action = ValidatorActionType.SAVE;
        String validatedField = TournamentSeasonTournamentIdFieldSetValidator.VALIDATED_FIELD;
        mockDoNothingOnValidateMethod(tournamentIdFieldSetValidator, season, action);
        final boolean isTournamentBeginDateSet = false;
        mockDoCallIsFieldSetValidateMethod(
                seasonBeginDateFieldSetValidator, season, action, validatedField, isTournamentBeginDateSet);
        mockDoNothingOnValidateMethod(tournamentExistValidator, season, action);

        Throwable thrown = catchThrowable(() -> {
            tournamentSeasonServiceValidator.validate(season, action);
        });

        String expectedMsg = buildFieldNotSetValidationMessage(action, validatedField);
        assertExceptionMessage(expectedMsg, FieldNotSetException.class, thrown);
    }

    @Test
    public void isRaiseExceptionWhenTournamentWithGivenIdNotExist() throws ValidationException {
        ValidatorActionType action = ValidatorActionType.SAVE;
        mockDoNothingOnValidateMethod(tournamentIdFieldSetValidator, season, action);
        mockDoNothingOnValidateMethod(seasonBeginDateFieldSetValidator, season, action);
        mockDoNothingOnValidateMethod(tournamentExistValidator, season, action);
        String validatedField = TournamentSeasonTournamentIdFieldSetValidator.VALIDATED_FIELD;
        String validatedParameter = TournamentSeasonTournamentExistValidator.VALIDATED_PARAMETER;
        final boolean isEntityExist = false;
        mockDoCallIsEntityExistValidateMethod(
                tournamentExistValidator, season, action, validatedField, validatedParameter, isEntityExist);

        Throwable thrown = catchThrowable(() -> {
            tournamentSeasonServiceValidator.validate(season, action);
        });

        String expectedMsg = buildEntityExistValidationMessage(action, validatedField, validatedParameter);
        assertExceptionMessage(expectedMsg, EntityNotExistException.class, thrown);
    }

    private TournamentSeason setupTournamentSeason() {
        Integer tournamentSeasonId = null;
        LocalDateTime beginDate = LocalDateTime.now();
        Boolean isOpen = Boolean.FALSE;
        Tournament targetTournament = null;

        return new TournamentSeason(tournamentSeasonId, beginDate, isOpen, targetTournament);
    }
}
