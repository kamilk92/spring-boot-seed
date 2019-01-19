package pl.kkp.core.db.service;

import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import pl.kkp.core.db.entity.User;
import pl.kkp.core.db.repository.UserRepository;
import pl.kkp.core.testing.SpringBootBaseTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;


public class TestUserService extends SpringBootBaseTest {
    @Mock
    private User mockUser;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    public void isFindUserByLogin() {
        String searchedLogin = "user";
        when(userRepository.findByLogin(searchedLogin)).thenReturn(mockUser);

        User foundUser = userService.findByLogin(searchedLogin);

        assertThat(foundUser).isEqualTo(mockUser);
        int expectedInvocationCnt = 1;
        verify(userRepository, times(expectedInvocationCnt)).findByLogin(searchedLogin);
    }

    @Test
    public void isUserSaveNewUser() {
        User userToSave = new User();
        when(userRepository.save(userToSave)).thenReturn(mockUser);

        User savedUser = userService.save(userToSave);

        assertThat(savedUser).isEqualTo(mockUser);
        int expectedInvocationCnt = 1;
        verify(userRepository, times(expectedInvocationCnt)).save(userToSave);
    }
}
