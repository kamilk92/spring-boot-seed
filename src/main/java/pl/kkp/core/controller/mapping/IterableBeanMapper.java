package pl.kkp.core.controller.mapping;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class IterableBeanMapper {

    @Autowired
    private DozerBeanMapper dozerBeanMapper;

    public <T, E> List<E> map(Iterable<T> elements, Class<? extends E> destClass) {
        return StreamSupport
                .stream(elements.spliterator(), false)
                .map(o -> dozerBeanMapper.map(o, destClass))
                .collect(Collectors.toCollection(LinkedList::new));
    }
}
