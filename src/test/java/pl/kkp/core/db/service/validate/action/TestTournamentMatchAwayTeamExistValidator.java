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

import java.util.Optional;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.when;
import static pl.kkp.core.testing.asserations.ExceptionAssertions.assertExceptionMessage;
import static pl.kkp.core.testing.mocks.EntityExistServiceValidatorMocks.buildEntityExistValidationMessage;

public class TestTournamentMatchAwayTeamExistValidator extends SpringBootBaseTest {

    private TournamentMatch match;

    @MockBean
    private TeamRepository teamRepository;

    @Autowired
    private TournamentMatchAwayTeamExistValidator matchAwayTeamExistValidator;

    @Before
    public void setUp() {
        match = setUpMatch();
    }

    @Test
    public void isPassWhenMatchAwayTeamExist() throws ValidationException {
        Integer awayTeamId = 0;
        Team awayTeam = new Team(awayTeamId);
        when(teamRepository.findById(awayTeamId)).thenReturn(Optional.of(awayTeam));
        match.setAwayTeam(awayTeam);
        ValidatorActionType action = ValidatorActionType.SAVE;

        matchAwayTeamExistValidator.validate(match, action);
    }

    @Test
    public void isRaiseExceptionWhenMatchcAwayTeamNotExist() {
        Integer awayTeamId = 0;
        Team awayTeam = new Team(awayTeamId);
        when(teamRepository.findById(awayTeamId)).thenReturn(Optional.empty());
        match.setAwayTeam(awayTeam);
        ValidatorActionType action = ValidatorActionType.SAVE;

        Throwable thrown = catchThrowable(() -> matchAwayTeamExistValidator.validate(match, action));

        String expectedMsg = buildEntityExistValidationMessage(matchAwayTeamExistValidator, action);
        assertExceptionMessage(expectedMsg, EntityNotExistException.class, thrown);
    }

    private TournamentMatch setUpMatch() {
        return new TournamentMatch();
    }

}
