package pl.kkp.core.db.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.kkp.core.db.entity.Tournament;
import pl.kkp.core.db.entity.TournamentSeason;
import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.action.TournamentSeasonTournamentIdFieldSetValidator;
import pl.kkp.core.db.service.validate.exception.ValidationException;
import pl.kkp.core.testing.SpringBootBaseTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static pl.kkp.core.testing.asserations.ExceptionAssertions.assertExceptionMessage;
import static pl.kkp.core.testing.mocks.FieldSetServiceValidatorMocks.buildFiledNotSetValidationMessage;

public class TestTournamentSeasonService extends SpringBootBaseTest {
    @MockBean
    private TournamentService mockTournamentService;

    private TournamentSeason tournamentSeason;

    @Autowired
    private TournamentSeasonService tournamentSeasonService;

    @Before
    public void setUp() {
        Integer seasonId = null;
        LocalDateTime beginDate = LocalDateTime.now();
        Boolean isOpen = Boolean.TRUE;
        Tournament tournament = null;
        tournamentSeason = new TournamentSeason(seasonId, beginDate, isOpen, tournament);
    }

    @Test
    public void isCreateNewTournamentSeason() throws ValidationException {
        Integer targetTournamentId = 0;
        Tournament tournament = new Tournament(targetTournamentId);
        tournamentSeason.setTournament(tournament);

        TournamentSeason createdTournamentSeason = tournamentSeasonService.save(tournamentSeason);

        assertThat(createdTournamentSeason).isNotNull();
        assertThat(createdTournamentSeason.getId()).isNotNull();
    }

    @Test
    public void isRaiseExceptionWhenTournamentIdNotSet() {
        Tournament tournament = new Tournament();
        tournamentSeason.setTournament(tournament);

        Throwable thrown = catchThrowable(() -> {
            tournamentSeasonService.save(tournamentSeason);
        });

        String expectedMessage = buildFiledNotSetValidationMessage(
                ValidatorActionType.SAVE, TournamentSeasonTournamentIdFieldSetValidator.VALIDATED_FIELD);
        assertExceptionMessage(expectedMessage, ValidationException.class, thrown);
    }
}
