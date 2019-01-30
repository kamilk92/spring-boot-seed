package pl.kkp.core.controller.mapping.test;

import org.springframework.beans.factory.annotation.Autowired;
import pl.kkp.core.controller.mapping.DozerUserRoleToRoleModelConverter;
import pl.kkp.core.controller.model.RoleModel;
import pl.kkp.core.db.entity.Role;
import pl.kkp.core.db.entity.User;
import pl.kkp.core.db.entity.UserRole;
import pl.kkp.core.testing.SpringBootBaseTest;

import static org.assertj.core.api.Assertions.assertThat;

public class TestUserRoleToRoleModelConverter extends SpringBootBaseTest {

    @Autowired
    private DozerUserRoleToRoleModelConverter userRoleToRoleModelConverter;

    public void isMapUserRoleToRoleModel() {
        String userLogin = "user";
        User user = new User(userLogin);
        String authority = "USER_ADMIN";
        Role role = new Role(authority);
        UserRole source = new UserRole(user, role);

        RoleModel roleModel = userRoleToRoleModelConverter.convertTo(source);

        assertThat(roleModel).isNotNull();
        assertThat(roleModel.getAuthority()).isEqualTo(authority);
    }

}
