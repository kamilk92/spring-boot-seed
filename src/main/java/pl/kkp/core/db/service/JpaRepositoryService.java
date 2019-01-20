package pl.kkp.core.db.service;

import org.springframework.data.repository.CrudRepository;
import pl.kkp.core.db.service.validate.exception.ValidationException;

public abstract class JpaRepositoryService<T, ID extends Number, E extends CrudRepository<T, ID>> {
    protected E entityRepository;

    public JpaRepositoryService(E entityRepository) {
        this.entityRepository = entityRepository;
    }

    public T save(T entity) throws ValidationException {
        return entityRepository.save(entity);
    }
}
