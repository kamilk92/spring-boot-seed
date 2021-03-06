package pl.kkp.core.db.service;

import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.kkp.core.db.entity.Tournament;
import pl.kkp.core.db.repository.TournamentRepository;
import pl.kkp.core.db.service.exception.EntityWithIdNotFoundException;
import pl.kkp.core.db.service.validate.ServiceValidator;
import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.exception.ValidationException;
import pl.kkp.core.testing.SpringBootBaseTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pl.kkp.core.testing.asserations.ExceptionAssertions.assertExceptionMessage;
import static pl.kkp.core.testing.mocks.ServiceValidatorMocks.buildEntityWithIdNotFoundValidationMessage;

public class TestTournamentService extends SpringBootBaseTest {
    @Mock
    private Tournament mockSavedTournament;

    @MockBean
    private TournamentRepository tournamentRepository;

    @MockBean
    private ServiceValidator<Tournament> tournamentServiceValidator;

    @Autowired
    private TournamentService tournamentService;

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
        ValidatorActionType action = ValidatorActionType.SAVE;
        doNothing().when(tournamentServiceValidator).validate(tournamentToSave, action);

        Tournament savedTournament = tournamentService.save(tournamentToSave);

        assertThat(savedTournament).isEqualTo(mockSavedTournament);
        int expectedInvocationCnt = 1;
        verify(tournamentRepository, times(expectedInvocationCnt)).save(tournamentToSave);
    }

    @Test
    public void isFindTournamentById() throws EntityWithIdNotFoundException {
        Integer tournamentId = 0;
        Optional<Tournament> foundResult = Optional.of(mockSavedTournament);
        when(tournamentRepository.findById(tournamentId)).thenReturn(foundResult);

        Tournament tournament = tournamentService.findById(tournamentId);

        assertThat(tournament).isEqualTo(mockSavedTournament);
    }

    @Test
    public void isRaiseExceptionWhenTournamentWithGivenIdNotExist() {
        Integer tournamentId = Integer.MAX_VALUE;
        Optional<Tournament> foundResult = Optional.empty();
        when(tournamentRepository.findById(tournamentId)).thenReturn(foundResult);

        Throwable thrown = catchThrowable(() -> {
            tournamentService.findById(tournamentId);
        });

        String expectedMessage = buildEntityWithIdNotFoundValidationMessage(tournamentId);
        assertExceptionMessage(expectedMessage, EntityWithIdNotFoundException.class, thrown);
    }

}
