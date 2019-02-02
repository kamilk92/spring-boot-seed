package pl.kkp.core.controller.mapping;

import org.dozer.DozerConverter;
import pl.kkp.core.db.entity.Role;

public class DozerUserRoleToRoleModelConverter extends DozerConverter<Role, String> {

    public DozerUserRoleToRoleModelConverter() {
        super(
                Role.class,
                String.class
        );
    }

    @Override
    public String convertTo(Role source, String destination) {
        return mapUserRole(source);
    }

    @Override
    public Role convertFrom(String source, Role destination) {
        return mapRole(source);
    }

    private String mapUserRole(Role source) {
        return source.getAuthority();
    }

    private Role mapRole(String source) {
        return new Role(source);
    }

}
