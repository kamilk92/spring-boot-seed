package pl.kkp.core.db.service.validate.action;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kkp.core.db.entity.Team;
import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.exception.FieldLengthTooLongException;
import pl.kkp.core.db.service.validate.exception.ValidationException;
import pl.kkp.core.testing.SpringBootBaseTest;
import pl.kkp.core.util.RandomStringGenerator;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static pl.kkp.core.testing.asserations.ExceptionAssertions.assertExceptionMessage;
import static pl.kkp.core.testing.mocks.FieldLengthServiceValidatorMocks.buildFieldTooLongValidationMessage;

public class TestTeamNameLengthValidator extends SpringBootBaseTest {

    private Team team;

    private RandomStringGenerator teamNameGenerator;

    @Autowired
    private TeamNameLengthValidator teamNameLengthValidator;

    public TestTeamNameLengthValidator() {
        teamNameGenerator = new RandomStringGenerator(TeamNameLengthValidator.MAX_LEN_INCLUSIVE + 1);
    }

    @Before
    public void setUp() {
        team = setUpTeam();
    }

    @Test
    public void isPassWhenTeamNameLengthIsValid() throws ValidationException {
        ValidatorActionType action = ValidatorActionType.SAVE;

        teamNameLengthValidator.validate(team, action);
    }

    @Test
    public void isRaiseWhenTeamNameFieldIsTooLong() {
        String teamName = teamNameGenerator.generate();
        team.setName(teamName);
        ValidatorActionType action = ValidatorActionType.SAVE;

        Throwable thrown = catchThrowable(() -> {
            teamNameLengthValidator.validate(team, action);
        });

        final int teamNameLength = teamName.length();
        String expectedMessage = buildFieldTooLongValidationMessage(action, teamNameLengthValidator, teamNameLength);
        assertExceptionMessage(expectedMessage, FieldLengthTooLongException.class, thrown);
    }

    private Team setUpTeam() {
        String name = "Team -413";

        return new Team(name);
    }
}
