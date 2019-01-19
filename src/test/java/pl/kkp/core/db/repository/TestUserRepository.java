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
    public void isFindUserByName() {
        String searchedName = "test-admin";

        User foundUser = userRepository.findByLogin(searchedName);

        assertThat(foundUser).isNotNull();
    }

    @Test
    public void isCreateNewUser() {
        Integer id = null;
        String login = "user";
        String password = "pass";
        String nick = "user-nick";
        String email = "user@domain.com";
        boolean isEnabled = true;
        User user = new User(id, login, password, nick, email, isEnabled);

        User createdUser = userRepository.save(user);

        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getId()).isNotNull();
        assertThat(createdUser.getLogin()).isEqualTo(user.getLogin());
        assertThat(createdUser.getPassword()).isEqualTo(user.getPassword());
        assertThat(createdUser.getNick()).isEqualTo(user.getNick());
        assertThat(createdUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(createdUser.getEnabled()).isEqualTo(user.getEnabled());
    }
}
