package pl.kkp.core.db.repository;

import org.springframework.data.repository.CrudRepository;
import pl.kkp.core.db.entity.User;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByLogin(String login);

    User findByEmail(String email);
}
