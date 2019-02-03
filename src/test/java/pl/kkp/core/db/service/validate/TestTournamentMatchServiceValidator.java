package pl.kkp.core.db.service.validate;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.kkp.core.db.entity.TournamentMatch;
import pl.kkp.core.db.service.validate.action.TournamentMatchAwayTeamExistValidator;
import pl.kkp.core.db.service.validate.action.TournamentMatchAwayTeamFieldSetValidator;
import pl.kkp.core.db.service.validate.action.TournamentMatchHomeTeamExistValidator;
import pl.kkp.core.db.service.validate.action.TournamentMatchHomeTeamFieldSetValidator;
import pl.kkp.core.db.service.validate.action.TournamentMatchIdFieldSetValidator;
import pl.kkp.core.db.service.validate.action.TournamentMatchSeasonExistValidator;
import pl.kkp.core.db.service.validate.action.TournamentMatchSeasonIdFieldSetValidator;
import pl.kkp.core.db.service.validate.exception.EntityNotExistException;
import pl.kkp.core.db.service.validate.exception.FieldNotSetException;
import pl.kkp.core.db.service.validate.exception.ValidationException;
import pl.kkp.core.testing.SpringBootBaseTest;

import javax.transaction.NotSupportedException;

import static org.assertj.core.api.Assertions.catchThrowable;
import static pl.kkp.core.testing.asserations.ExceptionAssertions.assertExceptionMessage;
import static pl.kkp.core.testing.mocks.EntityExistServiceValidatorMocks.buildEntityExistValidationMessage;
import static pl.kkp.core.testing.mocks.EntityExistServiceValidatorMocks.mockDoCallIsEntityExistValidateMethod;
import static pl.kkp.core.testing.mocks.FieldSetServiceValidatorMocks.buildFieldNotSetValidationMessage;
import static pl.kkp.core.testing.mocks.FieldSetServiceValidatorMocks.mockDoCallIsFieldSetValidateMethod;
import static pl.kkp.core.testing.mocks.ServiceValidatorMocks.mockDoNothingOnValidateMethod;

public class TestTournamentMatchServiceValidator extends SpringBootBaseTest {

    private TournamentMatch match;

    @Autowired
    private ServiceValidator<TournamentMatch> tournamentMatchServiceValidator;

    @MockBean
    private TournamentMatchAwayTeamFieldSetValidator matchAwayTeamFieldSetValidator;

    @MockBean
    private TournamentMatchAwayTeamExistValidator matchAwayTeamExistValidator;

    @MockBean
    private TournamentMatchHomeTeamExistValidator matchHomeTeamExistValidator;

    @MockBean
    private TournamentMatchHomeTeamFieldSetValidator matchHomeTeamFieldSetValidator;

    @MockBean
    private TournamentMatchIdFieldSetValidator matchIdFieldSetValidator;

    @MockBean
    private TournamentMatchSeasonExistValidator matchSeasonExistValidator;

    @MockBean
    private TournamentMatchSeasonIdFieldSetValidator matchSeasonIdFieldSetValidator;


    @Before
    public void setUp() {
        match = setUpMatch();
    }

    @Test
    public void isRaiseExceptionWhenMatchHomeTeamFieldNotSet() throws ValidationException {
        ValidatorActionType action = ValidatorActionType.SAVE;
        String validatedField = TournamentMatchHomeTeamFieldSetValidator.VALIDATED_FILED;
        final boolean isFieldSet = false;
        mockDoCallIsFieldSetValidateMethod(matchHomeTeamFieldSetValidator, match, action, validatedField, isFieldSet);
        mockDoNothingOnValidateMethod(matchHomeTeamExistValidator, match, action);
        mockDoNothingOnValidateMethod(matchAwayTeamFieldSetValidator, match, action);
        mockDoNothingOnValidateMethod(matchAwayTeamExistValidator, match, action);
        mockDoNothingOnValidateMethod(matchSeasonIdFieldSetValidator, match, action);
        mockDoNothingOnValidateMethod(matchSeasonExistValidator, match, action);

        Throwable thrown = catchThrowable(() -> tournamentMatchServiceValidator.validate(match, action));

        String expectedMsg = buildFieldNotSetValidationMessage(action, validatedField);
        assertExceptionMessage(expectedMsg, FieldNotSetException.class, thrown);
    }

    @Test
    public void isRaiseExceptionWhenHomeTeamNotExist() throws ValidationException {
        ValidatorActionType action = ValidatorActionType.SAVE;
        String validatedField = TournamentMatchHomeTeamExistValidator.VALIDATED_FIELD;
        String validatedParam = TournamentMatchHomeTeamExistValidator.VALIDATED_PARAMETER;
        final boolean isHomeTeamExist = false;
        mockDoNothingOnValidateMethod(matchHomeTeamFieldSetValidator, match, action);
        mockDoCallIsEntityExistValidateMethod(
                matchHomeTeamExistValidator, match, action, validatedField, validatedParam, isHomeTeamExist);
        mockDoNothingOnValidateMethod(matchAwayTeamFieldSetValidator, match, action);
        mockDoNothingOnValidateMethod(matchAwayTeamExistValidator, match, action);
        mockDoNothingOnValidateMethod(matchSeasonIdFieldSetValidator, match, action);
        mockDoNothingOnValidateMethod(matchSeasonExistValidator, match, action);

        Throwable thrown = catchThrowable(() -> tournamentMatchServiceValidator.validate(match, action));

        String expectedMsg = buildEntityExistValidationMessage(action, validatedField, validatedParam);
        assertExceptionMessage(expectedMsg, EntityNotExistException.class, thrown);
    }

    @Test
    public void isRaiseExceptionWhenAwayTeamFieldNotSet() throws ValidationException {
        ValidatorActionType action = ValidatorActionType.SAVE;
        String validatedField = TournamentMatchAwayTeamFieldSetValidator.VALIDATED_FIELD;
        mockDoNothingOnValidateMethod(matchHomeTeamFieldSetValidator, match, action);
        mockDoNothingOnValidateMethod(matchHomeTeamExistValidator, match, action);
        boolean isAwayTeamFieldSet = false;
        mockDoCallIsFieldSetValidateMethod(
                matchAwayTeamFieldSetValidator, match, action, validatedField, isAwayTeamFieldSet);
        mockDoNothingOnValidateMethod(matchAwayTeamExistValidator, match, action);
        mockDoNothingOnValidateMethod(matchSeasonIdFieldSetValidator, match, action);
        mockDoNothingOnValidateMethod(matchSeasonExistValidator, match, action);

        Throwable thrown = catchThrowable(() -> tournamentMatchServiceValidator.validate(match, action));

        String expectedMsg = buildFieldNotSetValidationMessage(action, validatedField);
        assertExceptionMessage(expectedMsg, FieldNotSetException.class, thrown);
    }

    @Test
    public void isRaiseExceptionWhenAwayTeamNotExist() throws ValidationException {
        ValidatorActionType action = ValidatorActionType.SAVE;
        String validatedField = TournamentMatchAwayTeamExistValidator.VALIDATED_FIELD;
        String validatedParameter = TournamentMatchAwayTeamExistValidator.VALIDATED_PARAMETER;
        mockDoNothingOnValidateMethod(matchAwayTeamFieldSetValidator, match, action);
        mockDoNothingOnValidateMethod(matchAwayTeamExistValidator, match, action);
        mockDoNothingOnValidateMethod(matchAwayTeamFieldSetValidator, match, action);
        final boolean isAwayTeamExist = false;
        mockDoCallIsEntityExistValidateMethod(
                matchAwayTeamExistValidator, match, action, validatedField, validatedParameter, isAwayTeamExist);
        mockDoNothingOnValidateMethod(matchSeasonIdFieldSetValidator, match, action);
        mockDoNothingOnValidateMethod(matchSeasonExistValidator, match, action);

        Throwable thrown = catchThrowable(() -> tournamentMatchServiceValidator.validate(match, action));

        String expectedMsg = buildEntityExistValidationMessage(action, validatedField, validatedParameter);
        assertExceptionMessage(expectedMsg, EntityNotExistException.class, thrown);
    }

    @Test
    public void isRaiseExceptionWhenMatchSeasonFieldNotSet() throws ValidationException {
        ValidatorActionType action = ValidatorActionType.SAVE;
        String validatedField = TournamentMatchSeasonIdFieldSetValidator.VALIDATED_FILED;
        mockDoNothingOnValidateMethod(matchAwayTeamFieldSetValidator, match, action);
        mockDoNothingOnValidateMethod(matchAwayTeamExistValidator, match, action);
        mockDoNothingOnValidateMethod(matchAwayTeamFieldSetValidator, match, action);
        mockDoNothingOnValidateMethod(matchAwayTeamExistValidator, match, action);
        final boolean isMatchSeasonSet = false;
        mockDoCallIsFieldSetValidateMethod(
                matchSeasonIdFieldSetValidator, match, action, validatedField, isMatchSeasonSet);
        mockDoNothingOnValidateMethod(matchSeasonExistValidator, match, action);

        Throwable thrown = catchThrowable(() -> tournamentMatchServiceValidator.validate(match, action));

        String expectedMsg = buildFieldNotSetValidationMessage(action, validatedField);
        assertExceptionMessage(expectedMsg, FieldNotSetException.class, thrown);
    }

    @Test
    public void isRaiseExceptionWhenMatchSeasonNotExist() throws ValidationException {
        ValidatorActionType action = ValidatorActionType.SAVE;
        mockDoNothingOnValidateMethod(matchAwayTeamFieldSetValidator, match, action);
        mockDoNothingOnValidateMethod(matchAwayTeamExistValidator, match, action);
        mockDoNothingOnValidateMethod(matchAwayTeamFieldSetValidator, match, action);
        mockDoNothingOnValidateMethod(matchAwayTeamExistValidator, match, action);
        mockDoNothingOnValidateMethod(matchSeasonIdFieldSetValidator, match, action);
        String validatedField = TournamentMatchSeasonIdFieldSetValidator.VALIDATED_FILED;
        String validatedParameter = TournamentMatchSeasonExistValidator.VALIDATED_PARAMETER;
        final boolean isMatchSeasonExist = false;
        mockDoCallIsEntityExistValidateMethod(
                matchSeasonExistValidator, match, action, validatedField, validatedParameter, isMatchSeasonExist);

        Throwable thrown = catchThrowable(() -> tournamentMatchServiceValidator.validate(match, action));

        String expectedMsg = buildEntityExistValidationMessage(action, validatedField, validatedParameter);
        assertExceptionMessage(expectedMsg, EntityNotExistException.class, thrown);
    }

    @Test
    public void isRaiseExceptionOnUpdateWhenMatchIdNotSet() throws ValidationException {
        ValidatorActionType action = ValidatorActionType.UPDATE;
        final String validatedField = TournamentMatchIdFieldSetValidator.VALIDATED_FILED;
        final boolean isMatchIdSet = false;
        mockDoCallIsFieldSetValidateMethod(matchIdFieldSetValidator, match, action, validatedField, isMatchIdSet);

        Throwable thrown = catchThrowable(() -> tournamentMatchServiceValidator.validate(match, action));

        String expectedMsg = buildFieldNotSetValidationMessage(action, validatedField);
        assertExceptionMessage(expectedMsg, FieldNotSetException.class, thrown);
    }

    private TournamentMatch setUpMatch() {
        return new TournamentMatch();
    }
}
