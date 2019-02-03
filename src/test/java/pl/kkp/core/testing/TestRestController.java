package pl.kkp.core.testing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import pl.kkp.core.controller.util.EndpointPathBuilder;
import pl.kkp.core.security.basic.http.BasicAuthorizedTestRestTemplate;
import pl.kkp.core.security.basic.http.BasicCredentials;

public abstract class TestRestController extends SpringBootBaseTest {
    private static final String APP_ADDRESS = "http://localhost";

    @Autowired
    protected BasicAuthorizedTestRestTemplate restTemplate;

    protected EndpointPathBuilder endpointPathBuilder;

    protected TestRestController(String endpointPath) {
        endpointPathBuilder = new EndpointPathBuilder(APP_ADDRESS, endpointPath);
    }

    protected <T> ResponseEntity<T> authorizedGet(
            BasicCredentials credentials, String endpointPath, Class<T> responseClass) {
        return restTemplate.authorizedGet(endpointPath, null, responseClass, credentials);
    }

    protected <T> ResponseEntity<T> authorizedGet(
            BasicCredentials credentials, String endpointPath, ParameterizedTypeReference<T> responseClass) {
        return restTemplate.authorizedGet(endpointPath, null, responseClass, credentials);
    }

    protected <T> ResponseEntity<T> authorizedPost(
            BasicCredentials credentials, String endpointPath, Object entity, Class<T> responseClass) {
        return restTemplate.authorizedPost(endpointPath, entity, responseClass, credentials);
    }

    protected <T> ResponseEntity<T> authorizedPut(
            BasicCredentials credentials, String endpointPath, Object entity, Class<T> responseClass) {
        return restTemplate.authorizedPut(endpointPath, entity, responseClass, credentials);
    }

    protected String getEndpointPath(String path) {
        return endpointPathBuilder.buildEndpointPath(path, portNumber);
    }

    protected String getServerPath(String path) {
        return endpointPathBuilder.buildServerPath(path, portNumber);
    }
}
