package pl.kkp.core.controller;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.kkp.core.controller.model.UserModel;
import pl.kkp.core.db.entity.User;
import pl.kkp.core.db.service.UserService;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private DozerBeanMapper dozerBeanMapper;

    @PostMapping
    public UserModel createUser(@RequestBody UserModel userModel) {
        User user = dozerBeanMapper.map(userModel, User.class);
        user = userService.save(user);

        return dozerBeanMapper.map(user, UserModel.class);
    }

    @GetMapping(path = "/login/{login}")
    public UserModel login(@PathVariable String login) {
        User user = userService.findByLogin(login);

        return dozerBeanMapper.map(user, UserModel.class);
    }
}
