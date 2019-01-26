package pl.kkp.core.security.basic.http;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class BasicAuthorizedTestRestTemplate extends TestRestTemplate {
    public <T> ResponseEntity<T> authorizedGet(
            String url,
            Object entity,
            Class<T> rspType,
            BasicCredentials credentials) {
        HttpMethod method = HttpMethod.GET;

        return authorizedRequest(url, method, entity, rspType, credentials);
    }

    public <T> ResponseEntity<T> authorizedPost(
            String url,
            Object entity,
            Class<T> rspType,
            BasicCredentials credentials
    ) {
        HttpMethod method = HttpMethod.POST;

        return authorizedRequest(url, method, entity, rspType, credentials);
    }

    public <T> ResponseEntity<T> authorizedRequest(
            String url,
            HttpMethod method,
            Object entity,
            Class<T> rspType,
            BasicCredentials credentials) {
        HttpHeaders headers = buildHeaderWithBasicAuthorization(credentials);
        HttpEntity<?> entityWithHeaders = new HttpEntity<>(entity, headers);

        return exchange(url, method, entityWithHeaders, rspType);
    }

    private HttpHeaders buildHeaderWithBasicAuthorization(BasicCredentials credentials) {
        String credentialsBase64 = credentials.toString();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", credentialsBase64);

        return headers;
    }
}
