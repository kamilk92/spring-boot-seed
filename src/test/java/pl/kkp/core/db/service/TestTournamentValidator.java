package pl.kkp.core.db.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.kkp.core.db.entity.Tournament;
import pl.kkp.core.db.repository.TournamentRepository;
import pl.kkp.core.db.service.validate.ServiceValidator;
import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.action.TournamentNameFieldSetValidator;
import pl.kkp.core.db.service.validate.action.TournamentNameUniqueValidator;
import pl.kkp.core.db.service.validate.exception.FieldNotSetException;
import pl.kkp.core.db.service.validate.exception.ValidationException;
import pl.kkp.core.testing.SpringBootBaseTest;
import pl.kkp.core.testing.mocks.FieldSetServiceValidatorMocks;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.Mockito.when;
import static pl.kkp.core.testing.asserations.ExceptionAssertaions.assertExceptionMessage;
import static pl.kkp.core.testing.mocks.FieldSetServiceValidatorMocks.buildFiledNotSetValidationMessage;
import static pl.kkp.core.testing.mocks.FieldSetServiceValidatorMocks.mockDoCallIsFieldSetValidateMethod;
import static pl.kkp.core.testing.mocks.ServiceValidatorMocks.mockDoNothingOnValidateMethod;
import static pl.kkp.core.testing.mocks.UniqueValueServiceValidatorMocks.buildUniqueValueValidationMessage;
import static pl.kkp.core.testing.mocks.UniqueValueServiceValidatorMocks.mockDoCallIsUniqueValidateMethod;

public class TestTournamentValidator extends SpringBootBaseTest {


    @MockBean
    private TournamentRepository tournamentRepository;

    @MockBean
    private TournamentNameFieldSetValidator tournamentNameFieldSetValidator;

    @MockBean
    private TournamentNameUniqueValidator tournamentNameUniqueValidator;

    @Autowired
    private ServiceValidator<Tournament> tournamentServiceValidator;

    private Tournament tournament;

    @Before
    public void setUp() {
        tournament = setUpTournament();
    }

    @Test
    public void isNotRaiseExceptionWhenTournamentNameIsUnique() throws ValidationException {
        ValidatorActionType action = ValidatorActionType.SAVE;
        mockDoNothingOnValidateMethod(tournamentNameFieldSetValidator, tournament, action);
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
        mockDoNothingOnValidateMethod(tournamentNameUniqueValidator, tournament, action);
        String validatedField = TournamentNameFieldSetValidator.VALIDATED_FIELD;
        final boolean isFieldSet = false;
        mockDoCallIsFieldSetValidateMethod(
                tournamentNameFieldSetValidator, tournament, action, validatedField, isFieldSet);

        Throwable thrown = catchThrowable(() -> {
            tournamentServiceValidator.validate(tournament, action);
        });

        String expectedMessage = buildFiledNotSetValidationMessage(action, validatedField);
        assertExceptionMessage(expectedMessage, FieldNotSetException.class, thrown);
    }

    private Tournament setUpTournament() {
        String name = "Test tournament";
        String description = "This is test tournament";
        return new Tournament(name, description);
    }

}
