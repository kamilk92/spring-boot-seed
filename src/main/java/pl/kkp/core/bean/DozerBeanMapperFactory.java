package pl.kkp.core.bean;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class DozerBeanMapperFactory {

    private static final List<String> MAPPING_FILES = Arrays.asList(
            "dozer/converters/dozer-mappings.xml",
            "dozer/converters/dozer-global-config.xml"
    );

    @Bean
    public DozerBeanMapper dozerBeanMapper() {
        DozerBeanMapper beanMapper = new DozerBeanMapper(MAPPING_FILES);

        return beanMapper;
    }
}
