package pl.kkp.core.bean;


import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.FactoryBean;

public class DozerBeanMapperFactory implements FactoryBean<DozerBeanMapper> {
    @Override
    public DozerBeanMapper getObject() throws Exception {
        return new DozerBeanMapper();
    }

    @Override
    public Class<?> getObjectType() {
        return DozerBeanMapper.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
