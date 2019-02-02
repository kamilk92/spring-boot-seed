package pl.kkp.core.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BasicRealmHeaderValueBuilderFactory {

    @Value("${security.realm.name}")
    private String realmName;

    @Bean
    public pl.kkp.core.security.basic.realm.BasicRealmHeaderValueBuilder basicRealmHeaderValueBuilder()
            throws Exception {
        return new pl.kkp.core.security.basic.realm.BasicRealmHeaderValueBuilder(realmName);
    }

}
