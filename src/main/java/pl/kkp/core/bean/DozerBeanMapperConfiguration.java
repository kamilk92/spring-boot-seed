package pl.kkp.core.bean;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DozerBeanMapperConfiguration {
    @Bean
    public DozerBeanMapperFactory dozerBeanMapperFactory() {
        return new DozerBeanMapperFactory();
    }

    @Bean
    public DozerBeanMapper dozerBeanMapper(DozerBeanMapperFactory dozerBeanMapperFactory) throws Exception {
        return dozerBeanMapperFactory.getObject();
    }
}
