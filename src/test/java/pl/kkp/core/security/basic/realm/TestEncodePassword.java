package pl.kkp.core.security.basic.realm;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.kkp.core.testing.SpringBootBaseTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TestEncodePassword extends SpringBootBaseTest {

    @Autowired
    private PasswordEncoder encoder;

    @Test
    public void encodePassword() {
        String password = "test-admin-password";
        String encoded = encoder.encode(password);

        assertThat(encoded).isNotEmpty();
    }
}
