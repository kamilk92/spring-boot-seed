package pl.kkp.core.controller.util;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TestEndpointPathBuilder {

    private static final String BASE_URL = "http://localhost";
    private static final String ENDPOINT_PATH = "/api";
    private static final int PORT = 8080;

    private EndpointPathBuilder endpointPathBuilder;

    @Before
    public void setUp() {
        endpointPathBuilder = new EndpointPathBuilder(BASE_URL, ENDPOINT_PATH);
    }

    @Test
    public void isReturnBasePath() {
        String builtPath = endpointPathBuilder.buildEndpointPath("", PORT);

        String expectedPath = String.format("%s:%d%s", BASE_URL, PORT, ENDPOINT_PATH);

        assertThat(builtPath).isEqualTo(expectedPath);
    }

    @Test
    public void isBuildEndpointPath() {
        String endpointSubPath = "user";

        String builtPath = endpointPathBuilder.buildEndpointPath(endpointSubPath, PORT);

        String expectedPath = String.format("%s:%d%s/%s", BASE_URL, PORT, ENDPOINT_PATH, endpointSubPath);
        assertThat(builtPath).isEqualTo(expectedPath);
    }

    @Test
    public void isBuildEndpointPathWhenSubPathGivenWithSlash() {
        String endpointPath = "/user";

        String builtPath = endpointPathBuilder.buildEndpointPath(endpointPath, PORT);

        String expectedPath = String.format("%s:%d%s%s", BASE_URL, PORT, ENDPOINT_PATH, endpointPath);
        assertThat(builtPath).isEqualTo(expectedPath);
    }
}
