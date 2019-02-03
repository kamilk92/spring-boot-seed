package pl.kkp.core.bean;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.kkp.core.testing.SpringBootBaseTest;

public class TestBCryptPasswordEncoder extends SpringBootBaseTest {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    public void isEncryptPassword(){
        String pass = "user";
        String encodedPass = passwordEncoder.encode(pass);
        System.out.println(encodedPass);
    }
}
