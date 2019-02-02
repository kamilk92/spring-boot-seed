package pl.kkp.core.controller.model;

import org.dozer.DozerBeanMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kkp.core.db.entity.Role;
import pl.kkp.core.db.entity.User;
import pl.kkp.core.db.entity.UserRole;
import pl.kkp.core.testing.SpringBootBaseTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


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

    @Test
    public void testMapUserWithRoles() {
        String[] authorities = new String[]{"ROLE_USER", "ROLE_ADMIN"};
        List<UserRole> userRoles = Arrays.stream(authorities)
                .map(this::buildUserRole)
                .collect(Collectors.toList());
        User user = new User(userRoles);

        UserModel userModel = dozerBeanMapper.map(user, UserModel.class);
        assertThat(userModel).isNotNull();
        assertThat(userModel.getAuthorities()).isNotEmpty();
    }

    private UserRole buildUserRole(String authority) {
        UserRole userRole = new UserRole();
        Role role = new Role(authority);
        userRole.setRole(role);

        return userRole;
    }
}
