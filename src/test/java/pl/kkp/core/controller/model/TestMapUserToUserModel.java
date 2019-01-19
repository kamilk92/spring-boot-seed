package pl.kkp.core.controller.model;

import org.assertj.core.api.AssertionsForClassTypes;
import org.dozer.DozerBeanMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kkp.core.db.entity.User;
import pl.kkp.core.testing.SpringBootBaseTest;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class TestMapUserToUserModel extends SpringBootBaseTest {
    @Autowired
    private DozerBeanMapper dozerBeanMapper;

    @Test
    public void isMapUserToUserModel() {
        Integer id = 10;
        String login = "user";
        String password = "password";
        String nick = "usernick";
        String email = "user@domain.com";
        Boolean isEnabled = true;
        User user = new User(id, login, password, nick, email, isEnabled);

        UserModel mappedUserModel = dozerBeanMapper.map(user, UserModel.class);

        assertThat(mappedUserModel).isNotNull();
        assertThat(mappedUserModel.getId()).isEqualTo(user.getId());
        assertThat(mappedUserModel.getLogin()).isEqualTo(user.getLogin());
        assertThat(mappedUserModel.getPassword()).isEqualTo(user.getPassword());
        assertThat(mappedUserModel.getNick()).isEqualTo(user.getNick());
        assertThat(mappedUserModel.getEmail()).isEqualTo(user.getEmail());
        assertThat(mappedUserModel.getEnabled()).isEqualTo(user.getEnabled());

    }
}
