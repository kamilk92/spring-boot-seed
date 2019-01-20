package pl.kkp.core.db.service;

import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import pl.kkp.core.db.entity.Tournament;
import pl.kkp.core.db.repository.TournamentRepository;
import pl.kkp.core.db.service.validate.exception.ValidationException;
import pl.kkp.core.testing.SpringBootBaseTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

public class TestTournamentService extends SpringBootBaseTest {
    @Mock
    private Tournament mockSavedTournament;

    @MockBean
    private TournamentRepository tournamentRepository;

    @Autowired
    private TournamentService tournamentService;

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
