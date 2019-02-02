package pl.kkp.core.bean;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kkp.core.security.basic.http.BasicCredentials;
import pl.kkp.core.testing.SpringBootBaseTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class TestBasicCredentialsFactory extends SpringBootBaseTest {

    @Autowired
    private BasicCredentials adminCredentials;

    @Test
    public void isProvideBasicCredentialsTestAdminUser() {
        assertThat(adminCredentials)
                .isNotNull();
        assertThat(adminCredentials.getUsername()).isEqualTo("test-admin");
        assertThat(adminCredentials.getPassword()).isEqualTo("test-admin-password");

    }
}
