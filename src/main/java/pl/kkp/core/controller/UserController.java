package pl.kkp.core.controller;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.kkp.core.controller.model.UserModel;
import pl.kkp.core.db.entity.User;
import pl.kkp.core.db.service.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private DozerBeanMapper dozerBeanMapper;

    @PostMapping(path = "/user")
    public UserModel createUser(@RequestBody UserModel userModel) {
        User user = dozerBeanMapper.map(userModel, User.class);
        user = userService.save(user);

        return dozerBeanMapper.map(user, UserModel.class);
    }
}
