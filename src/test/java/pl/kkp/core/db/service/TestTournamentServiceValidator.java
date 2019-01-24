package pl.kkp.core.db.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.kkp.core.db.entity.Tournament;
import pl.kkp.core.db.repository.TournamentRepository;
import pl.kkp.core.db.service.validate.ServiceValidator;
import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.action.TournamentNameFieldLengthValidator;
import pl.kkp.core.db.service.validate.action.TournamentNameFieldSetValidator;
import pl.kkp.core.db.service.validate.action.TournamentNameUniqueValidator;
import pl.kkp.core.db.service.validate.exception.FieldLengthTooLongException;
import pl.kkp.core.db.service.validate.exception.FieldNotSetException;
import pl.kkp.core.db.service.validate.exception.ValidationException;
import pl.kkp.core.testing.SpringBootBaseTest;
import pl.kkp.core.util.RandomStringGenerator;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.Mockito.when;
import static pl.kkp.core.testing.asserations.ExceptionAssertions.assertExceptionMessage;
import static pl.kkp.core.testing.mocks.FieldLengthServiceValidatorMocks.buildFieldTooLongValidationMessage;
import static pl.kkp.core.testing.mocks.FieldLengthServiceValidatorMocks.mockDoCallRealFieldLenValidateMethod;
import static pl.kkp.core.testing.mocks.FieldSetServiceValidatorMocks.buildFiledNotSetValidationMessage;
import static pl.kkp.core.testing.mocks.FieldSetServiceValidatorMocks.mockDoCallIsFieldSetValidateMethod;
import static pl.kkp.core.testing.mocks.ServiceValidatorMocks.mockDoNothingOnValidateMethod;
import static pl.kkp.core.testing.mocks.UniqueValueServiceValidatorMocks.buildUniqueValueValidationMessage;
import static pl.kkp.core.testing.mocks.UniqueValueServiceValidatorMocks.mockDoCallIsUniqueValidateMethod;

public class TestTournamentServiceValidator extends SpringBootBaseTest {

    private Tournament tournament;

    @MockBean
    private TournamentRepository tournamentRepository;

    @MockBean
    private TournamentNameFieldSetValidator tournamentNameFieldSetValidator;

    private RandomStringGenerator tournamentNameGenerator;

    @MockBean
    private TournamentNameUniqueValidator tournamentNameUniqueValidator;

    @MockBean
    private TournamentNameFieldLengthValidator tournamentNameFieldLengthValidator;

    @Autowired
    private ServiceValidator<Tournament> tournamentServiceValidator;

    public TestTournamentServiceValidator() {
        tournamentNameGenerator =
                new RandomStringGenerator(TournamentNameFieldLengthValidator.MAX_NAME_LENGTH + 1);
    }

    @Before
    public void setUp() {
        tournament = setUpTournament();
    }

    @Test
    public void isNotRaiseExceptionWhenTournamentNameIsUnique() throws ValidationException {
        ValidatorActionType action = ValidatorActionType.SAVE;
        mockDoNothingOnValidateMethod(tournamentNameFieldSetValidator, tournament, action);
        mockDoNothingOnValidateMethod(tournamentNameFieldLengthValidator, tournament, action);
        String validatedField = TournamentNameFieldSetValidator.VALIDATED_FIELD;
        final boolean isUniqueValue = true;
        mockDoCallIsUniqueValidateMethod(
                tournamentNameUniqueValidator, tournament, action, validatedField, isUniqueValue);

        when(tournamentRepository.findByName(tournament.getName())).thenReturn(null);

        tournamentServiceValidator.validate(tournament, action);
    }

    @Test
    public void isRaiseExceptionWhenTournamentNameIsNotUnique() throws ValidationException {
        ValidatorActionType action = ValidatorActionType.SAVE;
        mockDoNothingOnValidateMethod(tournamentNameFieldSetValidator, tournament, action);
        mockDoNothingOnValidateMethod(tournamentNameFieldLengthValidator, tournament, action);
        String validatedField = TournamentNameFieldSetValidator.VALIDATED_FIELD;
        final boolean isUniqueValue = false;
        mockDoCallIsUniqueValidateMethod(
                tournamentNameUniqueValidator, tournament, action, validatedField, isUniqueValue);

        Throwable thrown = catchThrowable(() -> {
            tournamentServiceValidator.validate(tournament, action);
        });

        String expectedMsg = buildUniqueValueValidationMessage(action, TournamentNameUniqueValidator.VALIDATED_FIELD);
        assertExceptionMessage(expectedMsg, ValidationException.class, thrown);
    }

    @Test
    public void isRaiseExceptionWhenTournamentNameFieldIsNotSet() throws ValidationException {
        tournament.setName("");
        ValidatorActionType action = ValidatorActionType.SAVE;
        String validatedField = TournamentNameFieldSetValidator.VALIDATED_FIELD;
        final boolean isFieldSet = false;
        mockDoCallIsFieldSetValidateMethod(
                tournamentNameFieldSetValidator, tournament, action, validatedField, isFieldSet);
        mockDoNothingOnValidateMethod(tournamentNameFieldLengthValidator, tournament, action);
        mockDoNothingOnValidateMethod(tournamentNameUniqueValidator, tournament, action);

        Throwable thrown = catchThrowable(() -> {
            tournamentServiceValidator.validate(tournament, action);
        });

        String expectedMessage = buildFiledNotSetValidationMessage(action, validatedField);
        assertExceptionMessage(expectedMessage, FieldNotSetException.class, thrown);
    }

    @Test
    public void isRaiseExceptionWhenTournamentNameTooLong() throws ValidationException {
        String tournamentName = tournamentNameGenerator.generate();
        tournament.setName(tournamentName);
        ValidatorActionType action = ValidatorActionType.SAVE;
        String validatedField = TournamentNameFieldLengthValidator.VALIDATED_FIELD;
        int maxPssLen = TournamentNameFieldLengthValidator.MAX_NAME_LENGTH;
        int minPassLen = 0;
        mockDoNothingOnValidateMethod(tournamentNameFieldSetValidator, tournament, action);
        mockDoCallRealFieldLenValidateMethod(
                tournamentNameFieldLengthValidator, tournament, action, validatedField, minPassLen, maxPssLen);
        mockDoNothingOnValidateMethod(tournamentNameUniqueValidator, tournament, action);

        Throwable thrown = catchThrowable(() -> {
            tournamentServiceValidator.validate(tournament, action);
        });

        int tournamentLen = tournamentName.length();
        String expectedMsg = buildFieldTooLongValidationMessage(
                action, tournamentNameFieldLengthValidator, tournamentLen);
        assertExceptionMessage(expectedMsg, FieldLengthTooLongException.class, thrown);
    }

    private Tournament setUpTournament() {
        String name = "Test tournament";
        String description = "This is test tournament";
        return new Tournament(name, description);
    }

}
