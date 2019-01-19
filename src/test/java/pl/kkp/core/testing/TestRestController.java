package pl.kkp.core.testing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Java6Assertions.assertThat;

public abstract class TestRestController extends SpringBootBaseTest {
    private static final String APP_ADDRESS = "http://localhost";

    @Autowired
    protected TestRestTemplate restTemplate;

    protected void assertResponseStatusCode(ResponseEntity response) {
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }


    protected String getEndpointAddress(String addressPath) {
        String pathDelimiter = addressPath.startsWith("/") ? "" : "/";

        return APP_ADDRESS + ":" + portNumber + pathDelimiter + addressPath;
    }
}
