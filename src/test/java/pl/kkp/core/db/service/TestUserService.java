package pl.kkp.core.db.service;

import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.kkp.core.db.entity.User;
import pl.kkp.core.db.repository.UserRepository;
import pl.kkp.core.db.service.validate.exception.ValidationException;
import pl.kkp.core.testing.SpringBootBaseTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


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
    public void isUserSaveNewUser() throws ValidationException {
        User userToSave = new User();
        when(userRepository.save(userToSave)).thenReturn(mockUser);

        User savedUser = userService.save(userToSave);

        assertThat(savedUser).isEqualTo(mockUser);
        int expectedInvocationCnt = 1;
        verify(userRepository, times(expectedInvocationCnt)).save(userToSave);
    }
}
