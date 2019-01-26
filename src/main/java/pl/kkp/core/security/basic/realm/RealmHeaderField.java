package pl.kkp.core.security.basic.realm;

public class RealmHeaderField {
    private String realmFieldName;
    private String realmValue;

    public RealmHeaderField(String realmFieldName, String realmValue) {
        this.realmFieldName = realmFieldName;
        this.realmValue = realmValue;
    }

    public String getRealmFieldName() {
        return realmFieldName;
    }

    public void setRealmFieldName(String realmFieldName) {
        this.realmFieldName = realmFieldName;
    }

    public String getRealmValue() {
        return realmValue;
    }

    public void setRealmValue(String realmValue) {
        this.realmValue = realmValue;
    }
}
