package pl.kkp.core.controller.mapping.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kkp.core.controller.mapping.IterableBeanMapper;
import pl.kkp.core.controller.model.UserModel;
import pl.kkp.core.db.entity.User;
import pl.kkp.core.testing.SpringBootBaseTest;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class TestIterableBeanMapper extends SpringBootBaseTest {

    @Autowired
    private IterableBeanMapper iterableBeanMapper;

    @Test
    public void isMapIterable() {
        String[] userNames = new String[]{
                "user0",
                "user1"
        };

        List<User> users = Arrays.stream(userNames)
                .map(User::new)
                .collect(Collectors.toList());

        List<UserModel> userModels = iterableBeanMapper.map(users, UserModel.class);

        assertThat(userModels)
                .size()
                .isGreaterThanOrEqualTo(2);

        assertThat(userModels).extracting("login").contains(userNames);
    }
}
