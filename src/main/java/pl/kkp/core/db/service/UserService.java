package pl.kkp.core.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kkp.core.db.entity.User;
import pl.kkp.core.db.repository.UserRepository;

@Service
public class UserService extends JpaRepositoryService<User, Integer, UserRepository> {

    @Autowired
    public UserService(UserRepository userRepository) {
        super(userRepository);
    }

    public User findByLogin(String login) {
        return entityRepository.findByLogin(login);
    }
}
