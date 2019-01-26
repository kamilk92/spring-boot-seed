package pl.kkp.core.security.basic.http;

import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TestBasicCredentials {
    @Test
    public void isReturnCredentialsStringAsEncodedInBase64() {
        String username = "admin";
        String password = "admin";

        BasicCredentials credentials = new BasicCredentials(username, password);
        String credentialsAsStr = credentials.toString();

        final String expectedEncodedCredentials = "Basic YWRtaW46YWRtaW4=";
        assertThat(credentialsAsStr)
                .isNotEmpty()
                .isEqualTo(expectedEncodedCredentials);
    }
}
