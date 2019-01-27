package pl.kkp.core.db.service.validate.action;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.kkp.core.db.entity.Team;
import pl.kkp.core.db.repository.TeamRepository;
import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.exception.NotUniqueValueException;
import pl.kkp.core.db.service.validate.exception.ValidationException;
import pl.kkp.core.testing.SpringBootBaseTest;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.Mockito.when;
import static pl.kkp.core.testing.asserations.ExceptionAssertions.assertExceptionMessage;
import static pl.kkp.core.testing.mocks.UniqueValueServiceValidatorMocks.buildUniqueValueValidationMessage;

public class TestTeamNameUniqueValidator extends SpringBootBaseTest {

    private Team team;

    @Autowired
    private TeamNameUniqueValidator teamNameUniqueValidator;

    @MockBean
    private TeamRepository teamRepository;

    @Before
    public void setUp() {
        team = setUpTeam();
    }

    @Test
    public void isPassWhenTeamNameIsUnique() throws ValidationException {
        String teamName = team.getName();
        when(teamRepository.findByName(teamName)).thenReturn(null);
        ValidatorActionType action = ValidatorActionType.SAVE;

        teamNameUniqueValidator.validate(team, action);
    }

    @Test
    public void isRaiseExceptionWhenTeamNameIsNotUnique() {
        String teamName = team.getName();
        when(teamRepository.findByName(teamName)).thenReturn(team);
        ValidatorActionType action = ValidatorActionType.SAVE;

        Throwable thrown = catchThrowable(() -> {
            teamNameUniqueValidator.validate(team, action);
        });

        String validatedField = TeamNameLengthValidator.VALIDATED_FIELD;
        String expectedMessage = buildUniqueValueValidationMessage(action, validatedField);
        assertExceptionMessage(expectedMessage, NotUniqueValueException.class, thrown);
    }

    private Team setUpTeam() {
        String teamName = "Tottenham -331";

        return new Team(teamName);
    }

}
