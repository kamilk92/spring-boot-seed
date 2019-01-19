package pl.kkp.core.testing;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Java6Assertions.assertThat;

public abstract class TestRestController extends SpringBootBaseTest {
    private static final String APP_ADDRESS = "http://localhost";

    @Autowired
    protected TestRestTemplate restTemplate;

    protected String endpointBasePathFormat;

    protected TestRestController(String endpointBasePath) {
        endpointBasePathFormat = buildEndpointBasePathFormat(endpointBasePath);
    }

    protected void assertResponseStatusCode(ResponseEntity response) {
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    protected String getEndpointPath(String path) {
        String endpointBasePath = getEndpointBasePath(this.portNumber);
        path = buildPath(path);

        return new StringBuilder(endpointBasePath)
                .append(path)
                .toString();
    }

    protected String getEndpointBasePath(Integer port) {
        return String.format(this.endpointBasePathFormat, port);
    }

    private String buildEndpointBasePathFormat(String basePath) {
        basePath = buildPath(basePath);
        String portNumDelimiter = ":";
        String portNumFormat = "%d";

        return new StringBuilder(APP_ADDRESS)
                .append(portNumDelimiter)
                .append(portNumFormat)
                .append(basePath)
                .toString();
    }

    private String buildPath(String path) {
        if (path.startsWith("/")) {
            return path;
        }
        if (StringUtils.isNotEmpty(path)) {
            return "/" + path;
        }

        return "";
    }
}
