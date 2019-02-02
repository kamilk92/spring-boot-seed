package pl.kkp.core.db.service;

import org.springframework.data.repository.CrudRepository;
import pl.kkp.core.db.service.exception.EntityWithIdNotFoundException;
import pl.kkp.core.db.service.validate.ServiceValidator;
import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.exception.ValidationException;

import java.util.Optional;

public abstract class JpaRepositoryService<T, K extends Number, E extends CrudRepository<T, K>> {
    protected E entityRepository;
    protected ServiceValidator<T> serviceValidator;

    public JpaRepositoryService(E entityRepository) {
        this.entityRepository = entityRepository;
    }

    public JpaRepositoryService(E entityRepository, ServiceValidator<T> serviceValidator) {
        this.entityRepository = entityRepository;
        this.serviceValidator = serviceValidator;
    }

    public Iterable<T> findAll() {
        return entityRepository.findAll();
    }

    public T findById(K id) throws EntityWithIdNotFoundException {
        Optional<T> foundEntity = entityRepository.findById(id);

        return foundEntity.orElseThrow(() -> new EntityWithIdNotFoundException(id));
    }

    public T save(T entity) throws ValidationException {
        return entityRepository.save(entity);
    }

    public T validateAndSave(T entity) throws ValidationException {
        serviceValidator.validate(entity, ValidatorActionType.SAVE);

        return entityRepository.save(entity);
    }
}
