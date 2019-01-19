package pl.kkp.core.db.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kkp.core.db.entity.User;
import pl.kkp.core.testing.TestJpa;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TestUserRepository extends TestJpa {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void isCreateNewUserAndThenFindByLogin() {
        Integer id = null;
        String login = "user";
        String password = "pass";
        String nick = "user-nick";
        String email = "user@domain.com";
        boolean isEnabled = true;

        User createdUser = saveUser(id, login, password, nick, email, isEnabled);

        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getId()).isNotNull();
        assertThat(createdUser.getLogin()).isEqualTo(login);
        assertThat(createdUser.getPassword()).isEqualTo(password);
        assertThat(createdUser.getNick()).isEqualTo(nick);
        assertThat(createdUser.getEmail()).isEqualTo(email);
        assertThat(createdUser.getEnabled()).isEqualTo(isEnabled);

        User foundUser = userRepository.findByLogin(login);

        assertThat(foundUser).isEqualTo(createdUser);
    }

    private User saveUser(Integer id, String login, String password, String nick, String email, Boolean isEnabled) {
        User user = new User(id, login, password, nick, email, isEnabled);

        return userRepository.save(user);
    }
}
