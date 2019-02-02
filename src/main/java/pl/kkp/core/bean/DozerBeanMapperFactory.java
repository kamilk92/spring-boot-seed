package pl.kkp.core.bean;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

@Configuration
public class DozerBeanMapperFactory {

    private static final List<String> MAPPING_FILES = Arrays.asList(
            "dozer-mappings.xml"
    );

    @Bean
    public DozerBeanMapper dozerBeanMapper() throws FileNotFoundException {
        DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
        dozerBeanMapper.setMappingFiles(MAPPING_FILES);

        return dozerBeanMapper;
    }
}
