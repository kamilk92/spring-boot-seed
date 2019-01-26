package pl.kkp.core.security.basic.http;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class BasicCredentials {
    private String username;
    private String password;

    public BasicCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        String credentials = String.format("%s:%s", username, password);
        Charset charset = StandardCharsets.UTF_8;
        byte[] credentialsBytes = credentials.getBytes(charset);
        byte[] decodedCredentialsBytes = Base64.getEncoder().encode(credentialsBytes);
        String decodedCredentialString = new String(decodedCredentialsBytes, charset);

        return String.format("Basic %s", decodedCredentialString);
    }
}
