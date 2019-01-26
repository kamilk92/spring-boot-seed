package pl.kkp.core.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import pl.kkp.core.security.basic.http.BasicCredentials;

@Configuration
public class BasicCredentialsFactory {
    @Value("${security.test.basic.username}")
    private String testAdminUserName;

    @Value("${security.test.basic.password}")
    private String testAdminPassword;

    @Bean
    @Scope(scopeName = BeanDefinition.SCOPE_PROTOTYPE)
    public BasicCredentials adminCredentials() {
        return new BasicCredentials(testAdminUserName, testAdminPassword);
    }
}
