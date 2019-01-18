package pl.kkp.core.testing;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public abstract class TestRestController {
    public static final Integer APPLICATION_PORT = 8080;
    public static final String APPLICATION_ADDR = "http://localhost:" + APPLICATION_PORT;
}
