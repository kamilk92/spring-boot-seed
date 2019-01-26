package pl.kkp.core.controller;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import pl.kkp.core.security.basic.http.BasicCredentials;
import pl.kkp.core.testing.TestRestController;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class TestMainController extends TestRestController {
    private static final String ENDPOINT_PATH = "";

    @Autowired
    private BasicCredentials adminCredentials;

    public TestMainController() {
        super(ENDPOINT_PATH);
    }

    @Test
    public void isIndexPageResponse() {
        String endpointPath = getEndpointPath("");

        HttpEntity<String> response = authorizedGet(adminCredentials, endpointPath, String.class);

        String responseBody = response.getBody();
        String expectedResponse = MainController.INDEX_MESSAGE;
        assertThat(responseBody).isEqualTo(expectedResponse);
    }

}
