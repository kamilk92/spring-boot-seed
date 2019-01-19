package pl.kkp.core.db.service;

import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.kkp.core.db.entity.User;
import pl.kkp.core.db.repository.UserRepository;
import pl.kkp.core.testing.SpringBootBaseTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;


public class TestUserService extends SpringBootBaseTest {
    @Mock
    private User mockSavedUser;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    public void isUserSaveNewUser() {
        User userToSave = new User();
        when(userRepository.save(userToSave)).thenReturn(mockSavedUser);

        User savedUser = userService.save(userToSave);

        assertThat(savedUser).isEqualTo(mockSavedUser);
        int expectedInvocationCnt = 1;
        verify(userRepository, times(expectedInvocationCnt)).save(userToSave);
    }
}
