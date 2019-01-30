package pl.kkp.core.controller.mapping;

import org.dozer.DozerConverter;
import org.springframework.stereotype.Component;
import pl.kkp.core.controller.model.RoleModel;
import pl.kkp.core.db.entity.Role;
import pl.kkp.core.db.entity.UserRole;

@Component
public class DozerUserRoleToRoleModelConverter extends DozerConverter<UserRole, RoleModel> {

    public DozerUserRoleToRoleModelConverter() {
        super(UserRole.class, RoleModel.class);
    }

    @Override
    public RoleModel convertTo(UserRole source, RoleModel destination) {
        if ((source == null) || (source.getRole() == null)) {
            return null;
        }
        String authority = source.getRole().getAuthority();

        return new RoleModel(authority);
    }

    @Override
    public UserRole convertFrom(RoleModel source, UserRole destination) {
        if ((source == null) || (source.getAuthority() == null)) {
            return null;
        }
        String authority = source.getAuthority();
        Role role = new Role(authority);

        return new UserRole(null, role);
    }
}
