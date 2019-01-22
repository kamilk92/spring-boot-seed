package pl.kkp.core.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kkp.core.db.entity.User;
import pl.kkp.core.db.repository.UserRepository;
import pl.kkp.core.db.service.validate.exception.ValidationException;

@Service
public class UserService extends JpaRepositoryService<User, Integer, UserRepository> {

    @Autowired
    public UserService(UserRepository userRepository) {
        super(userRepository);
    }

    public User findByLogin(String login) {
        return entityRepository.findByLogin(login);
    }

    public User save(User user) throws ValidationException {
        user = new User(user);
        user.setEnabled(true);

        return super.save(user);
    }
}