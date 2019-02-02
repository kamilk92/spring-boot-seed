package pl.kkp.core.controller;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.kkp.core.controller.mapping.IterableBeanMapper;
import pl.kkp.core.controller.model.UserModel;
import pl.kkp.core.db.entity.User;
import pl.kkp.core.db.service.UserService;
import pl.kkp.core.db.service.validate.exception.ValidationException;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private DozerBeanMapper dozerBeanMapper;

    @Autowired
    private IterableBeanMapper iterableBeanMapper;

    @PostMapping(path = "/user")
    public UserModel createUser(@RequestBody UserModel userModel) throws ValidationException {
        User user = dozerBeanMapper.map(userModel, User.class);
        user = userService.save(user);

        return dozerBeanMapper.map(user, UserModel.class);
    }

    @GetMapping(path = "/user")
    public UserModel login(@RequestParam String login) {
        User user = userService.findByLogin(login);

        return dozerBeanMapper.map(user, UserModel.class);
    }

    @GetMapping(path = "/users")
    public List<UserModel> getAllUsers() {
        Iterable<User> users = userService.findAll();
        List<UserModel> userModels = iterableBeanMapper.map(users, UserModel.class);

        return userModels;
    }
}
