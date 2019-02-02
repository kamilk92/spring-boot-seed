package pl.kkp.core.db.service.validate.action;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.kkp.core.db.entity.Team;
import pl.kkp.core.db.entity.TournamentMatch;
import pl.kkp.core.db.repository.TeamRepository;
import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.exception.EntityNotExistException;
import pl.kkp.core.db.service.validate.exception.ValidationException;
import pl.kkp.core.testing.SpringBootBaseTest;
import pl.kkp.core.testing.asserations.ExceptionAssertions;
import pl.kkp.core.testing.mocks.EntityExistServiceValidatorMocks;

import java.util.Optional;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.when;
import static pl.kkp.core.testing.asserations.ExceptionAssertions.assertExceptionMessage;
import static pl.kkp.core.testing.mocks.EntityExistServiceValidatorMocks.buildEntityExistValidationMessage;

public class TestTournamentMatchHomeTeamExistValidator extends SpringBootBaseTest {

    private TournamentMatch match;

    @Autowired
    private TournamentMatchHomeTeamExistValidator homeTeamExistValidator;

    @MockBean
    private TeamRepository teamRepository;

    @Before
    public void setUp(){
        match = setUpMatch();
    }

    @Test
    public void isPassWhenMatchHomeTeamExist() throws ValidationException {
        Integer homeTeamId = 0;
        Team homeTeam = new Team(homeTeamId);
        when(teamRepository.findById(homeTeamId)).thenReturn(Optional.of(homeTeam));
        match.setHomeTeam(homeTeam);
        ValidatorActionType action = ValidatorActionType.SAVE;

        homeTeamExistValidator.validate(match, action);
    }

    @Test
    public void isRaiseExceptionWhenMatchHomeTeamNotExist(){
        Integer homeTeamId = 0;
        Team homeTeam = new Team(homeTeamId);
        when(teamRepository.findById(homeTeamId)).thenReturn(Optional.empty());
        match.setHomeTeam(homeTeam);
        ValidatorActionType action = ValidatorActionType.SAVE;

        Throwable thrown = catchThrowable(() -> homeTeamExistValidator.validate(match, action));

        String expectedMsg = buildEntityExistValidationMessage(homeTeamExistValidator, action);
        assertExceptionMessage(expectedMsg, EntityNotExistException.class, thrown);
    }

    private TournamentMatch setUpMatch(){
        return new TournamentMatch();
    }

}
