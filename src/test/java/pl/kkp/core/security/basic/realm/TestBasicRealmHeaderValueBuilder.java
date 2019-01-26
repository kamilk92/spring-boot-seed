package pl.kkp.core.security.basic.realm;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kkp.core.testing.SpringBootBaseTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TestBasicRealmHeaderValueBuilder extends SpringBootBaseTest {

    @Autowired
    private RealmHeaderValueBuilder basicRealmHeaderValueBuilder;

    @Test
    public void isBuildBasicRealmHeaderField() {

        RealmHeaderField realmHeader = basicRealmHeaderValueBuilder.build();

        assertThat(realmHeader).isNotNull();
        assertThat(realmHeader.getRealmFieldName()).isEqualTo(BasicRealmHeaderValueBuilder.HEADER_REALM_FIELD_NAME);
        String expectedRealmName = "TOURNAMENT APPLICATION REALM";
        assertThat(realmHeader.getRealmValue()).isEqualTo(expectedRealmName);
    }

}
