package pl.kkp.core.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kkp.core.db.entity.User;
import pl.kkp.core.db.repository.UserRepository;
import pl.kkp.core.db.service.validate.ServiceValidator;
import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.exception.ValidationException;

@Service
public class UserService extends JpaRepositoryService<User, Integer, UserRepository> {

    @Autowired
    private UserService(UserRepository userRepository) {
        super(userRepository);
    }

    @Autowired
    private ServiceValidator<User> userServiceValidator;

    public User findByLogin(String login) {
        return entityRepository.findByLogin(login);
    }

    public User save(User user) throws ValidationException {
        userServiceValidator.validate(user, ValidatorActionType.SAVE);
        user = new User(user);
        user.setEnabled(true);

        return super.save(user);
    }
}
