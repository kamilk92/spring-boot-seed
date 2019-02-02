package pl.kkp.core.db.service.validate;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.kkp.core.db.entity.Team;
import pl.kkp.core.db.service.validate.action.TeamNameFieldSetValidator;
import pl.kkp.core.db.service.validate.action.TeamNameLengthValidator;
import pl.kkp.core.db.service.validate.action.TeamNameUniqueValidator;
import pl.kkp.core.db.service.validate.exception.FieldLengthTooLongException;
import pl.kkp.core.db.service.validate.exception.FieldNotSetException;
import pl.kkp.core.db.service.validate.exception.NotUniqueValueException;
import pl.kkp.core.db.service.validate.exception.ValidationException;
import pl.kkp.core.testing.SpringBootBaseTest;
import pl.kkp.core.util.RandomStringGenerator;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static pl.kkp.core.testing.asserations.ExceptionAssertions.assertExceptionMessage;
import static pl.kkp.core.testing.mocks.FieldLengthServiceValidatorMocks.buildFieldTooLongValidationMessage;
import static pl.kkp.core.testing.mocks.FieldLengthServiceValidatorMocks.mockDoCallRealFieldLenValidateMethod;
import static pl.kkp.core.testing.mocks.FieldSetServiceValidatorMocks.buildFieldNotSetValidationMessage;
import static pl.kkp.core.testing.mocks.FieldSetServiceValidatorMocks.mockDoCallIsFieldSetValidateMethod;
import static pl.kkp.core.testing.mocks.ServiceValidatorMocks.mockDoNothingOnValidateMethod;
import static pl.kkp.core.testing.mocks.UniqueValueServiceValidatorMocks.buildUniqueValueValidationMessage;
import static pl.kkp.core.testing.mocks.UniqueValueServiceValidatorMocks.mockDoCallIsUniqueValidateMethod;

public class TestTeamServiceValidator extends SpringBootBaseTest {

    private Team team;

    private RandomStringGenerator teamNameGenerator;

    @Autowired
    private ServiceValidator<Team> teamServiceValidator;

    @MockBean
    private TeamNameFieldSetValidator mockTeamNameFieldSetValidator;

    @MockBean
    private TeamNameLengthValidator mockTeamNameLengthValidator;

    @MockBean
    private TeamNameUniqueValidator mockTeamNameUniqueValidator;

    public TestTeamServiceValidator() {
        teamNameGenerator = new RandomStringGenerator(TeamNameLengthValidator.MAX_LEN_INCLUSIVE + 1);
    }

    @Before
    public void setUp() {
        team = setUpTeam();
    }

    @Test
    public void isRaiseExceptionWhenTeamNameIsTooLong() throws ValidationException {
        String teamName = teamNameGenerator.generate();
        team.setName(teamName);

        ValidatorActionType action = ValidatorActionType.SAVE;
        mockDoNothingOnValidateMethod(mockTeamNameFieldSetValidator, team, action);
        String validatedField = TeamNameLengthValidator.VALIDATED_FIELD;
        int minLen = TeamNameLengthValidator.MIN_LEN_INCLUSIVE;
        int maxLen = TeamNameLengthValidator.MAX_LEN_INCLUSIVE;
        mockDoCallRealFieldLenValidateMethod(mockTeamNameLengthValidator, team, action, validatedField, minLen, maxLen);
        mockDoNothingOnValidateMethod(mockTeamNameUniqueValidator, team, action);

        Throwable thrown = catchThrowable(() -> {
            teamServiceValidator.validate(team, action);
        });

        final int actualLen = teamName.length();
        String expectedMessage = buildFieldTooLongValidationMessage(action, validatedField, maxLen, actualLen);
        assertExceptionMessage(expectedMessage, FieldLengthTooLongException.class, thrown);
    }

    @Test
    public void isRaiseExceptionWhenTeamNameNotSet() throws ValidationException {
        team.setName(null);
        ValidatorActionType action = ValidatorActionType.SAVE;
        String validatedField = TeamNameFieldSetValidator.VALIDATED_FIELD;
        final boolean isFieldSet = false;
        mockDoCallIsFieldSetValidateMethod(mockTeamNameFieldSetValidator, team, action, validatedField, isFieldSet);
        mockDoNothingOnValidateMethod(mockTeamNameLengthValidator, team, action);
        mockDoNothingOnValidateMethod(mockTeamNameUniqueValidator, team, action);

        Throwable thrown = catchThrowable(() -> {
            teamServiceValidator.validate(team, action);
        });

        String expectedMessage = buildFieldNotSetValidationMessage(action, validatedField);
        assertExceptionMessage(expectedMessage, FieldNotSetException.class, thrown);
    }

    @Test
    public void isRaiseExceptionWhenTeamNameIsNotUnique() throws ValidationException {
        ValidatorActionType action = ValidatorActionType.SAVE;
        mockDoNothingOnValidateMethod(mockTeamNameFieldSetValidator, team, action);
        mockDoNothingOnValidateMethod(mockTeamNameLengthValidator, team, action);
        String validatedField = TeamNameUniqueValidator.VALIDATED_FIELD;
        final boolean isUniqueValue = false;
        mockDoCallIsUniqueValidateMethod(mockTeamNameUniqueValidator, team, action, validatedField, isUniqueValue);

        Throwable thrown = catchThrowable(() -> {
            teamServiceValidator.validate(team, action);
        });

        String expectedMessage = buildUniqueValueValidationMessage(action, validatedField);
        assertExceptionMessage(expectedMessage, NotUniqueValueException.class, thrown);
    }

    private Team setUpTeam() {
        String name = "Team unique 1410";

        return new Team(name);
    }
}
