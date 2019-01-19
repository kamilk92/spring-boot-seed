package pl.kkp.core.bean;

import org.dozer.DozerBeanMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kkp.core.testing.SpringBootBaseTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TestDozerBeanMapperFactory extends SpringBootBaseTest {
    @Autowired
    private DozerBeanMapper dozerBeanMapper;

    @Test
    public void isDozerBeanFactoryReturnDozerBeanMapper() {
        assertThat(dozerBeanMapper).isNotNull();
    }
}
