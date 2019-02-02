package pl.kkp.core.controller.mapping.test;

import org.dozer.DozerBeanMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kkp.core.controller.model.UserModel;
import pl.kkp.core.db.entity.Role;
import pl.kkp.core.db.entity.User;
import pl.kkp.core.db.entity.UserRole;
import pl.kkp.core.testing.SpringBootBaseTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class TestMapUserAuthorities extends SpringBootBaseTest {

    @Autowired
    private DozerBeanMapper mapper;

    @Test
    public void isMapUserRolesToUserModelAuthorities() {
        List<String> roleNames = new ArrayList<String>() {
            {
                add("ROLE_USER");
                add("ROLE_ADMIN");
            }
        };
        List<UserRole> userRoles = roleNames.stream()
                .map(role -> new UserRole(new Role(role)))
                .collect(Collectors.toList());
        User user = new User(userRoles);

        UserModel userModel = mapper.map(user, UserModel.class);

        assertThat(userModel).isNotNull();
        assertThat(userModel.getAuthorities())
                .isNotEmpty()
                .hasSize(2)
                .extracting("authority")
                .containsAll(roleNames);
    }
}
