package pl.kkp.core.db.service;

import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.kkp.core.db.entity.Tournament;
import pl.kkp.core.db.repository.TournamentRepository;
import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.action.TournamentNameUniqueValidator;
import pl.kkp.core.db.service.validate.exception.NotUniqueValueException;
import pl.kkp.core.db.service.validate.exception.ValidationException;
import pl.kkp.core.testing.SpringBootBaseTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pl.kkp.core.testing.asserations.ExceptionAssertaions.assertExceptionMessage;
import static pl.kkp.core.testing.mocks.UniqueValueServiceValidatorMocks.buildUniqueValueValidationMessage;

public class TestTournamentService extends SpringBootBaseTest {
    @Mock
    private Tournament mockSavedTournament;

    @MockBean
    private TournamentRepository tournamentRepository;

    @Autowired
    private TournamentService tournamentService;

    @Test
    public void isRaiseExceptionWhenTournamentNameNotUnique() throws ValidationException {
        String tournamentName = "Test Tournament";
        when(tournamentService.findByName(tournamentName)).thenReturn(mockSavedTournament);
        when(mockSavedTournament.getName()).thenReturn(tournamentName);

        Throwable thrown = catchThrowable(() -> {
            tournamentService.save(mockSavedTournament);
        });

        String expectedMessage = buildUniqueValueValidationMessage(
                ValidatorActionType.SAVE, TournamentNameUniqueValidator.VALIDATED_FIELD);
        assertExceptionMessage(expectedMessage, NotUniqueValueException.class, thrown);
    }

    @Test
    public void isReturnTournamentByName() {
        String tournamentName = "Test tournament";

        when(tournamentRepository.findByName(tournamentName)).thenReturn(mockSavedTournament);
        Tournament tournament = tournamentService.findByName(tournamentName);

        assertThat(tournament).isNotNull();
        assertThat(mockSavedTournament).isEqualTo(tournament);
    }

    @Test
    public void isSaveNewTournament() throws ValidationException {
        Tournament tournamentToSave = new Tournament();
        when(tournamentRepository.save(tournamentToSave)).thenReturn(mockSavedTournament);

        Tournament savedTournament = tournamentService.save(tournamentToSave);

        assertThat(savedTournament).isEqualTo(mockSavedTournament);
        int expectedInvocationCnt = 1;
        verify(tournamentRepository, times(expectedInvocationCnt)).save(tournamentToSave);
    }
}
