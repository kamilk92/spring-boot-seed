package pl.kkp.core.controller.model;

public class RoleModel {
    private String authority;

    public RoleModel() {
    }

    public RoleModel(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
