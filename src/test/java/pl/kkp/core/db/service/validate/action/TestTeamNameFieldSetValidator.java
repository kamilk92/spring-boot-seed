package pl.kkp.core.db.service.validate.action;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kkp.core.db.entity.Team;
import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.exception.FieldNotSetException;
import pl.kkp.core.testing.SpringBootBaseTest;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static pl.kkp.core.testing.asserations.ExceptionAssertions.assertExceptionMessage;
import static pl.kkp.core.testing.mocks.FieldSetServiceValidatorMocks.buildFiledNotSetValidationMessage;

public class TestTeamNameFieldSetValidator extends SpringBootBaseTest {

    private Team team;

    @Autowired
    private TeamNameFieldSetValidator teamNameFieldSetValidator;

    @Before
    public void setUp() {
        team = setUpTeam();
    }

    @Test
    public void isPassWhenTemNameSet() throws FieldNotSetException {
        ValidatorActionType action = ValidatorActionType.SAVE;

        teamNameFieldSetValidator.validate(team, action);
    }

    @Test
    public void isRaiseExceptionWhenTeamNameNotSet() {
        team.setName("");
        ValidatorActionType action = ValidatorActionType.SAVE;

        Throwable thrown = catchThrowable(() -> {
            teamNameFieldSetValidator.validate(team, action);
        });

        String validatedField = TeamNameFieldSetValidator.VALIDATED_FIELD;
        String expectedMessage = buildFiledNotSetValidationMessage(action, validatedField);
        assertExceptionMessage(expectedMessage, FieldNotSetException.class, thrown);
    }

    private Team setUpTeam() {
        Team team = new Team();
        team.setName("Team unique -490");

        return team;
    }
}
