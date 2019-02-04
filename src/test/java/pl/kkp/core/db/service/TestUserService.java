package pl.kkp.core.db.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.kkp.core.db.entity.User;
import pl.kkp.core.db.repository.UserRepository;
import pl.kkp.core.db.service.validate.exception.ValidationException;
import pl.kkp.core.testing.SpringBootBaseTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class TestUserService extends SpringBootBaseTest {

    @Mock
    private User mockUser;

    @MockBean
    private BCryptPasswordEncoder passwordEncoder;

    private User user;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Before
    public void setUp() {
        user = setupUser();
    }

    @Test
    public void isFindUserByLogin() {
        String searchedLogin = "test-admin";
        when(userRepository.findByLogin(searchedLogin)).thenReturn(user);

        User foundUser = userService.findByLogin(searchedLogin);

        assertThat(foundUser).isEqualTo(user);
        int expectedInvocationCnt = 1;
        verify(userRepository, times(expectedInvocationCnt)).findByLogin(searchedLogin);
    }

    @Test
    public void isUserSaveNewUser() throws ValidationException {
        when(userRepository.save(any(User.class))).thenReturn(mockUser);
        String encodedPass = "encodePassValue";
        when(passwordEncoder.encode(user.getPassword())).thenReturn(encodedPass);

        User savedUser = userService.save(user);

        assertThat(savedUser).isEqualTo(mockUser);
        int expectedInvocationCnt = 1;
        verify(userRepository, times(expectedInvocationCnt)).save(any(User.class));

        verify(userRepository).save(userCaptor.capture());
        User calledUser = userCaptor.getValue();
        assertThat(calledUser).isInstanceOf(User.class);
        assertThat(calledUser.getLogin()).isEqualTo(user.getLogin());
        assertThat(calledUser.getPassword()).isEqualTo(encodedPass);
        assertThat(calledUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(calledUser.getEnabled()).isEqualTo(true);
        String plainPassword = user.getPassword();
        verify(passwordEncoder, times(1)).encode(plainPassword);
    }

    public User setupUser() {
        Integer userId = null;
        String login = "user123";
        String password = "password123";
        String nick = null;
        String email = "user@domain.com";
        boolean isEnabled = false;

        return new User(userId, login, password, nick, email, isEnabled);
    }

}
