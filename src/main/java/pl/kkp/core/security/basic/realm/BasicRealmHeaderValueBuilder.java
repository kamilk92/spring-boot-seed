package pl.kkp.core.security.basic.realm;

public class BasicRealmHeaderValueBuilder implements RealmHeaderValueBuilder {
    public static final String HEADER_REALM_FIELD_NAME = "WWW-Authenticate";

    private String realName;

    public BasicRealmHeaderValueBuilder(String realName) {
        this.realName = realName;
    }

    @Override
    public RealmHeaderField build() {
        return new RealmHeaderField(HEADER_REALM_FIELD_NAME, realName);
    }
}
