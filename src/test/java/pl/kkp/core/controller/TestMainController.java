package pl.kkp.core.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import pl.kkp.core.testing.TestRestController;


public class TestMainController extends TestRestController {

    @Autowired
    private MainController mainController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void isIndexPageResponse() {
        String response = restTemplate.getForObject(TestRestController.APPLICATION_ADDR, String.class);

        String expectedResponse = MainController.INDEX_MESSAGE;
        assertThat(response).isEqualTo(expectedResponse);
    }

}
