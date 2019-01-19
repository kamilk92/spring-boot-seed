package pl.kkp.core.controller;

import org.junit.Test;
import pl.kkp.core.testing.TestRestController;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class TestMainController extends TestRestController {
    private static final String ENDPOINT_PATH = "";

    public TestMainController() {
        super(ENDPOINT_PATH);
    }

    @Test
    public void isIndexPageResponse() {
        String endpointPath = getEndpointPath("");
        String response = restTemplate.getForObject(endpointPath, String.class);

        String expectedResponse = MainController.INDEX_MESSAGE;
        assertThat(response).isEqualTo(expectedResponse);
    }

}
