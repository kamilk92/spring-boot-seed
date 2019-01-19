package pl.kkp.core.db.service;

import org.springframework.data.repository.CrudRepository;

public class JpaRepositoryService<T, ID extends Number> {
    protected CrudRepository<T, ID> entityRepository;

    public JpaRepositoryService(CrudRepository<T, ID> entityRepository) {
        this.entityRepository = entityRepository;
    }

    public T save(T entity) {
        return entityRepository.save(entity);
    }
}
