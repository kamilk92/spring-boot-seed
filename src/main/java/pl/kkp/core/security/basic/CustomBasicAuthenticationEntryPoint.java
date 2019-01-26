package pl.kkp.core.security.basic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import pl.kkp.core.security.basic.realm.RealmHeaderField;
import pl.kkp.core.security.basic.realm.RealmHeaderValueBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CustomBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    private RealmHeaderField realmHeaderField;

    public CustomBasicAuthenticationEntryPoint(RealmHeaderValueBuilder basicRealmHeaderValueBuilder) {
        this.realmHeaderField = basicRealmHeaderValueBuilder.build();
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx)
            throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        addRealmHeaderValue(response);
        PrintWriter writer = response.getWriter();
        writer.println("Unauthorized access, 401 - " + authEx.getMessage());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        String realmName = realmHeaderField.getRealmValue();
        setRealmName(realmName);
        super.afterPropertiesSet();
    }

    private void addRealmHeaderValue(HttpServletResponse response) {
        String realmHeaderFieldName = realmHeaderField.getRealmFieldName();
        String realmName = realmHeaderField.getRealmValue();
        response.addHeader(realmHeaderFieldName, realmName);
    }
}
